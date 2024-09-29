package com.elastic.plant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "plant_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlantImage {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String plantId;

    @Field(type = FieldType.Keyword)
    private String url;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String caption;

    @Field(type = FieldType.Keyword)
    private String type;

}