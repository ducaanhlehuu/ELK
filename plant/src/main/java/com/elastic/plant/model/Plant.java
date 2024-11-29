package com.elastic.plant.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.util.List;

@Mapping
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(indexName = "plants_v12")
public class Plant {

    @Field(type = FieldType.Text, analyzer = "short_text_analyzer", similarity = "scripted_tfidf")
    @JsonProperty("scientific_name")
    private String scientificName;

    @Field(type = FieldType.Text, analyzer = "short_text_analyzer", similarity = "my_bm25_similarity")
    @JsonProperty("english_name")
    private String englishName;

    @Field(type = FieldType.Text, analyzer = "short_text_analyzer", similarity = "my_bm25_similarity")
    @JsonProperty("vietnamese_name")
    private List<String> vietnameseNames;

    @Field(type = FieldType.Text, analyzer = "short_text_analyzer", similarity = "my_bm25_similarity")
    @JsonProperty("other_names")
    private List<String> otherNames;

    @Field(type = FieldType.Text, similarity = "scripted_tfidf")
    @JsonProperty("division")
    private String division;

    @Field(type = FieldType.Text, analyzer = "custom_text_analyzer", similarity = "my_bm25_similarity")
    @JsonProperty("division_description")
    private String divisionDescription;

    @Field(type = FieldType.Text, similarity = "scripted_tfidf")
    @JsonProperty("_class")
    private String classType;

    @Field(type = FieldType.Text, analyzer = "custom_text_analyzer", similarity = "my_bm25_similarity")
    @JsonProperty("_class_description")
    private String classDescription;

    @Field(type = FieldType.Text, similarity = "scripted_tfidf")
    @JsonProperty("order")
    private String order;

    @Field(type = FieldType.Text, analyzer = "custom_text_analyzer", similarity = "my_bm25_similarity")
    @JsonProperty("order_description")
    private String orderDescription;

    @Field(type = FieldType.Text, similarity = "scripted_tfidf")
    @JsonProperty("family")
    private String family;

    @Field(type = FieldType.Text, analyzer = "custom_text_analyzer", similarity = "my_bm25_similarity")
    @JsonProperty("family_description")
    private String familyDescription;

    @Field(type = FieldType.Text, similarity = "scripted_tfidf")
    @JsonProperty("genus")
    private String genus;

    @Field(type = FieldType.Text, analyzer = "custom_text_analyzer", similarity = "my_bm25_similarity")
    @JsonProperty("genus_description")
    private String genusDescription;

    @Field(type = FieldType.Text, analyzer = "custom_text_analyzer", similarity = "my_bm25_similarity")
    @JsonProperty("description")
    private String description;

}
