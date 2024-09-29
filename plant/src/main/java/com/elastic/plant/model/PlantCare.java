package com.elastic.plant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "plant_care")
public class PlantCare {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String plantId;

    @Field(type = FieldType.Keyword)
    private String plantingTime;

    @Field(type = FieldType.Keyword)
    private List<String> soilType;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String fertilizer;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String pruning;

    @Field(type = FieldType.Keyword)
    private List<String> pests;

    @Field(type = FieldType.Keyword)
    private List<String> diseases;

}