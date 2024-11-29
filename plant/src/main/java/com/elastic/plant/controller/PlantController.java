package com.elastic.plant.controller;

import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.json.JsonpMappingException;
import com.elastic.plant.model.Plant;
import com.elastic.plant.model.PlantResponse;
import com.elastic.plant.service.PlantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            IndexResponse response = plantService.indexPlant(plant);
            logger.info("Plant indexed successfully: {}", plant);
            return ResponseEntity.ok("Plant indexed successfully with id: " + response.id());
        } catch (IOException e) {
            logger.error("Error indexing plant: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error indexing plant: " + e.getMessage());
        }
    }

//    @GetMapping("/search")
//    public ResponseEntity<List<Plant>> searchPlants(@RequestParam @Nullable String query) {
//        try {
//            if (query == null) {
//                query = "";
//            }
//            var response = plantService.searchPlants(query);
//            List<Plant> plants = response.hits().hits().stream()
//                    .map(hit -> hit.source())
//                    .toList();
//            logger.info("Search results for query '{}': {}", query, plants);
//            return ResponseEntity.ok(plants);
//        } catch (IOException e) {
//            logger.error("Error searching plants: {}", e.getMessage(), e);
//            return ResponseEntity.status(500).body(null);
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantResponse> getPlantById(@PathVariable String id) {
        try {
            PlantResponse plant = plantService.getPlantById(id);
            if (plant != null) {
                return ResponseEntity.ok(plant);
            } else {
                logger.warn("Plant not found for ID: {}", id);
                return ResponseEntity.notFound().build();
            }
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
    @GetMapping("/search_string")
    public ResponseEntity<List<PlantResponse>> searchPlantsByString(@RequestParam String query) {
        try {
            var response = plantService.searchByQueryString(query);
            List<PlantResponse> plants = response.hits().hits().stream()
                    .map(hit -> PlantResponse.builder()
                            .plant(hit.source())
                            .id(hit.id())
                            .score(hit.score())
                            .build())
                    .toList();
            logger.info("Search results for string '{}': {}", query, plants);
            return ResponseEntity.ok(plants);
        } catch (IOException e) {
            logger.error("Error searching plants by string: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/search_string/{pageNum}")
    public ResponseEntity<List<PlantResponse>> searchPlantsByStringByPage(@RequestParam String query, @PathVariable("pageNum") Integer pageNum) {
        try {
            if (pageNum == null || pageNum < 1) {
                pageNum = 1;
            }
            var response = plantService.searchByQueryWithPagination(query, pageNum);
            List<PlantResponse> plants = response.hits().hits().stream()
                    .map(hit -> PlantResponse.builder()
                            .plant(hit.source())
                            .id(hit.id())
                            .score(hit.score())
                            .build())
                    .toList();
            logger.info("Search results for string '{}': {}", query, plants);
            return ResponseEntity.ok(plants);
        } catch (IOException e) {
            logger.error("Error searching plants by string: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
//
//    @GetMapping("/search/field")
//    public ResponseEntity<List<Plant>> searchByField(@RequestParam String fieldName, @RequestParam String keyword) {
//        try {
//            var response = plantService.searchByField(fieldName, keyword);
//            List<Plant> plants = response.hits().hits().stream()
//                    .map(hit -> hit.source())
//                    .toList();
//            logger.info("Search results for field '{}': {} with keyword '{}'", fieldName, plants, keyword);
//            return ResponseEntity.ok(plants);
//        } catch (IOException e) {
//            logger.error("Error searching plants by field: {}", e.getMessage(), e);
//            return ResponseEntity.status(500).body(null);
//        }
//    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePlant(@PathVariable String id, @RequestBody Plant updatedPlant) {
        try {
            UpdateResponse updated = plantService.updatePlant(id, updatedPlant);
            if (updated != null) {
                Map<String, Object> body = new HashMap<>();
                body.put("msg", "Plant updated successfully");
                body.put("plant",PlantResponse.builder()
                        .plant(updatedPlant)
                        .id(updated.id())
                        .build());
                return ResponseEntity.ok(body);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error updating plant: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Plant>> getAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        try {
            var response = plantService.getAllPlants(pageNum, pageSize);
            List<Plant> plants = response.hits().hits().stream()
                    .map(hit -> hit.source())
                    .toList();
            return ResponseEntity.ok(plants);
        } catch (IOException e) {
            logger.error("Error GET plants by field: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
}
