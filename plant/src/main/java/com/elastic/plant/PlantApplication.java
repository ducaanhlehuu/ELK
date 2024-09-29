package com.elastic.plant;

import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.elastic.plant.model.Plant;
import com.elastic.plant.service.PlantService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.io.IOException;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.elastic.plant.repository")
public class PlantApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlantApplication.class, args);
    }

}
