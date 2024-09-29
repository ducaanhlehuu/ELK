package com.elastic.plant.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import org.apache.http.HttpHost;
import org.apache.http.Header;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import javax.net.ssl.SSLContext;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;

public class ElasticsearchClientProvider {
    private static ElasticsearchClient client;
    public static ElasticsearchClient getClient() {
        if (client == null) {
            String serverUrl = "https://localhost:9200";
            String apiKey = "djdrNk9wSUJ4azM2aGgyM0JKQlY6RG5BRlNqVTdUU1dqd0JHTENZcUMyQQ==";

            try {
                SSLContext sslContext = new SSLContextBuilder()
                        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .build();

                RestClient restClient = RestClient
                        .builder(HttpHost.create(serverUrl))
                        .setDefaultHeaders(new Header[]{
                                new BasicHeader("Authorization", "ApiKey " + apiKey)
                        })
                        .setHttpClientConfigCallback(httpClientBuilder ->
                                httpClientBuilder.setSSLContext(sslContext)
                                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE))
                        .build();

                RestClientTransport transport = new RestClientTransport(
                        restClient, new JacksonJsonpMapper());

                client = new ElasticsearchClient(transport);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return client;
    }
}
