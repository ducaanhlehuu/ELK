package com.elastic.plant.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlantResponse {
    private String id;
    private Double score;
    private Long totalResults;
    private Plant plant;
}
