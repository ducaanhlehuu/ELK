package com.elastic.plant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.elastic.plant.repository")
public class PlantApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(PlantApplication.class, args);
    }


}
