package com.elastic.plant.service;

import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.elastic.plant.elastic.ElasticsearchClientProvider;
import com.elastic.plant.model.Plant;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PlantService {

    public IndexResponse indexPlant(Plant plant) throws IOException {

        return ElasticsearchClientProvider.getClient().index(i -> i
                .index("plants")
                .id(plant.getCommonName() +"-" +  plant.getScientificName())
                .document(plant));
    }

    public SearchResponse<Plant> searchPlants(String query) throws IOException {
        return ElasticsearchClientProvider.getClient().search(s -> s
                        .index("plants")
                        .query(q -> q
                                .queryString(qs -> qs
                                        .query(query))),
                Plant.class);
    }

    public Optional<Plant> getPlantById(String id) throws IOException {
        GetResponse<Plant> response = ElasticsearchClientProvider.getClient().get(g -> g
                .index("plants")
                .id(id), Plant.class);
        System.out.println(response.toString());
        return response.found() ? Optional.of(response.source()) : Optional.empty();
    }

    public SearchResponse<Plant> searchByDescription(String description) throws IOException {
        return ElasticsearchClientProvider.getClient().search(s -> s
                        .index("plants")
                        .query(q -> q
                                .match(m -> m
                                        .field("description")
                                        .query(description))),
                Plant.class);
    }

    public SearchResponse<Plant> searchByName(String name) throws IOException {
        return ElasticsearchClientProvider.getClient().search(s -> s
                        .index("plants")
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
                        .index("plants")
                        .query(q -> q
                                .term(t -> t
                                        .field(fieldName)
                                        .value(keyword))),
                Plant.class);
    }
}
