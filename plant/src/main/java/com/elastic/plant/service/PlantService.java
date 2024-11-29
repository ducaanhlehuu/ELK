package com.elastic.plant.service;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.elastic.plant.elastic.ElasticsearchClientProvider;
import com.elastic.plant.model.Plant;
import com.elastic.plant.model.PlantResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlantService {

    public static String INDEX_NAME = "plants_v12";

    public IndexResponse indexPlant(Plant plant) throws IOException {
        return ElasticsearchClientProvider.getClient().index(i -> i
                .index(INDEX_NAME)
                .id(UUID.randomUUID().toString())
                .document(plant));
    }

    public SearchResponse<Plant> searchByQueryWithPagination(String query, int pageNum) throws IOException {
        int pageSize = 10;

        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Search query cannot be null or empty");
        }

        if (pageNum < 1) {
            throw new IllegalArgumentException("Page number must be 1 or greater");
        }

        int from = (pageNum - 1) * pageSize;

        try {
            return ElasticsearchClientProvider.getClient().search(
                    s -> s
                            .index(INDEX_NAME)
                            .from(from)
                            .size(pageSize)
                            .query(q -> q.queryString(qs -> qs
                                    .query(query)
                                    .defaultField("*")
                                    .analyzeWildcard(true)
                            ))
                            .source(src -> src.filter(f -> f.includes("_id", "*"))),
                    Plant.class
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public SearchResponse<Plant> searchByQueryString(String query) throws IOException {
        return ElasticsearchClientProvider.getClient().search(s -> s
                        .index(INDEX_NAME)
                        .query(q -> q
                                .queryString(qs -> qs
                                        .query(query)
                                        .defaultField("*")
                                )
                        ),
                Plant.class);
    }

    public SearchResponse<Plant> searchPlants(String query) throws IOException {
        return ElasticsearchClientProvider.getClient().search(s -> s
                        .index(INDEX_NAME)
                        .query(q -> q
                                .queryString(qs -> qs.analyzer("custom_text_analyzer")
                                        .query(query))),
                Plant.class);
    }

    public PlantResponse getPlantById(String id) throws IOException {
        GetResponse<Plant> response = ElasticsearchClientProvider.getClient().get(g -> g
                .index(INDEX_NAME)
                .id(id), Plant.class);
        System.out.println(response.toString());
        if (response.found()) {
            return PlantResponse.builder().id(response.id()).totalResults(1L)
                    .score(null).plant(response.source()).build();
        }
        return null;
    }

    public SearchResponse<Plant> searchByDescription(String description) throws IOException {
        return ElasticsearchClientProvider.getClient().search(s -> s
                        .index(INDEX_NAME)
                        .query(q -> q
                                .match(m -> m
                                        .field("description")
                                        .query(description))),
                Plant.class);
    }

    public SearchResponse<Plant> searchByName(String name) throws IOException {
        return ElasticsearchClientProvider.getClient().search(s -> s
                        .index(INDEX_NAME)
                        .query(q -> q
                                .multiMatch(m -> m
                                        .fields("commonName", "scientificName")
                                        .query(name) // Tìm kiếm theo tên
                                )
                        ),
                Plant.class);
    }
    public SearchResponse<Plant> searchByField(String fieldName, String keyword) throws IOException {
        return ElasticsearchClientProvider.getClient().search(s -> s
                        .index(INDEX_NAME)
                        .query(q -> q
                                .term(t -> t
                                        .field(fieldName)
                                        .value(keyword))),
                Plant.class);
    }

    public UpdateResponse updatePlant(String id, Plant updatedPlant) throws IOException {
        try {
            return ElasticsearchClientProvider.getClient().update(u -> u
                            .index(INDEX_NAME)
                            .id(id)
                            .doc(updatedPlant),
                    Plant.class);
        } catch (ElasticsearchException e) {
            if (e.getMessage() != null && e.getMessage().contains("document_missing_exception")) {
                String errorMsg = "Plant with ID " + id + " does not exist.";
                throw new IOException(errorMsg, e);
            }
            throw new IOException("Failed to update plant with ID: " + id, e);
        }
    }

    public SearchResponse<Plant> getAllPlants(int pageNum, int pageSize) throws IOException {
        int from = (pageNum - 1) * pageSize;

        return ElasticsearchClientProvider.getClient().search(s -> s
                        .index(INDEX_NAME)
                        .from(from)
                        .size(pageSize)
                        .query(q -> q
                                .matchAll(MatchAllQuery.of(m -> m))
                        ),
                Plant.class);
    }
}
