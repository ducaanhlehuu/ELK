package com.elastic.plant.controller;

import co.elastic.clients.json.JsonpMappingException;
import com.elastic.plant.model.Plant;
import com.elastic.plant.service.PlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private static final Logger logger = LoggerFactory.getLogger(PlantController.class);

    @Autowired
    private PlantService plantService;

    @PostMapping("/index")
    public ResponseEntity<String> indexPlant(@RequestBody Plant plant) {
        try {
            plantService.indexPlant(plant);
            logger.info("Plant indexed successfully: {}", plant);
            return ResponseEntity.ok("Plant indexed successfully");
        } catch (IOException e) {
            logger.error("Error indexing plant: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error indexing plant: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Plant>> searchPlants(@RequestParam @Nullable String query) {
        try {
            if (query == null) {
                query = "";
            }
            var response = plantService.searchPlants(query);
            List<Plant> plants = response.hits().hits().stream()
                    .map(hit -> hit.source())
                    .toList();
            logger.info("Search results for query '{}': {}", query, plants);
            return ResponseEntity.ok(plants);
        } catch (IOException e) {
            logger.error("Error searching plants: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlantById(@PathVariable String id) {
        try {
            Optional<Plant> plant = plantService.getPlantById(id);
            return plant.map(ResponseEntity::ok)
                    .orElseGet(() -> {
                        logger.warn("Plant not found for ID: {}", id);
                        return ResponseEntity.notFound().build();
                    });
        } catch (IOException e) {
            logger.error("IO error while fetching plant: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        } catch (JsonpMappingException e) {
            logger.error("JSON mapping error: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Plant>> searchPlantsByName(@RequestParam String name) {
        try {
            var response = plantService.searchByName(name);
            List<Plant> plants = response.hits().hits().stream()
                    .map(hit -> hit.source())
                    .toList();
            logger.info("Search results for name '{}': {}", name, plants);
            return ResponseEntity.ok(plants);
        } catch (IOException e) {
            logger.error("Error searching plants by name: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search/field")
    public ResponseEntity<List<Plant>> searchByField(@RequestParam String fieldName, @RequestParam String keyword) {
        try {
            var response = plantService.searchByField(fieldName, keyword);
            List<Plant> plants = response.hits().hits().stream()
                    .map(hit -> hit.source())
                    .toList();
            logger.info("Search results for field '{}': {} with keyword '{}'", fieldName, plants, keyword);
            return ResponseEntity.ok(plants);
        } catch (IOException e) {
            logger.error("Error searching plants by field: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

}
