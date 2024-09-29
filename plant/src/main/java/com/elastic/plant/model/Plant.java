package com.elastic.plant.model;


import com.elastic.plant.model.subfield.Dimension;
import com.elastic.plant.model.subfield.Flowers;
import com.elastic.plant.model.subfield.Fruit;
import com.elastic.plant.model.subfield.Leaves;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Document(indexName = "plants")
@AllArgsConstructor
@NoArgsConstructor

public class Plant {

    @Id
    @Schema(description = "ID duy nhất của thực vật.")
    private String id;

    @Field(type = FieldType.Text, analyzer = "standard")
    @Schema(description = "Tên phổ biến của thực vật, được sử dụng rộng rãi bởi người dân.")
    private String commonName;

    @Field(type = FieldType.Text, analyzer = "standard")
    @Schema(description = "Tên khoa học của thực vật, theo hệ thống phân loại.")
    private String scientificName;

    @Field(type = FieldType.Keyword)
    @Schema(description = "Gia đình thực vật mà thực vật thuộc về.")
    private String family;

    @Field(type = FieldType.Keyword)
    @Schema(description = "Loại thực vật (ví dụ: cây, bụi cây, hoa, v.v.).")
    private String type;

    @Field(type = FieldType.Keyword)
    @Schema(description = "Danh sách các địa điểm hoặc quốc gia mà thực vật có nguồn gốc.")
    private List<String> origin;

    @Field(type = FieldType.Keyword)
    @Schema(description = "Danh sách các vùng khí hậu phù hợp cho sự phát triển của thực vật.")
    private List<String> zones;

    @Field(type = FieldType.Keyword)
    @Schema(description = "Danh sách các ứng dụng của thực vật (ví dụ: thực phẩm, trang trí, thuốc, v.v.).")
    private List<String> uses;

    @Field(type = FieldType.Nested)
    @Schema(description = "Thông tin về chiều cao của thực vật.")
    private Dimension height;

    @Field(type = FieldType.Nested)
    @Schema(description = "Thông tin về chiều rộng của thực vật.")
    private Dimension spread;

    @Field(type = FieldType.Keyword)
    @Schema(description = "Yêu cầu về ánh sáng mặt trời của thực vật (ví dụ: nắng đầy đủ, bóng râm, v.v.).")
    private List<String> sunRequirements;

    @Field(type = FieldType.Keyword)
    @Schema(description = "Sở thích về lượng nước của thực vật (ví dụ: khô, vừa phải, ẩm ướt).")
    private List<String> waterPreferences;

    @Field(type = FieldType.Nested)
    @Schema(description = "Thông tin về lá của thực vật.")
    private Leaves leaves;

    @Field(type = FieldType.Nested)
    @Schema(description = "Thông tin về hoa của thực vật.")
    private Flowers flowers;

    @Field(type = FieldType.Nested)
    @Schema(description = "Thông tin về trái cây mà thực vật có thể sản xuất.")
    private Fruit fruit;

    @Field(type = FieldType.Keyword)
    @Schema(description = "Các phương pháp nhân giống thực vật (ví dụ: hạt, giâm cành, phân nhánh, v.v.).")
    private List<String> propagationMethods;

    @Field(type = FieldType.Keyword)
    @Schema(description = "Các thông tin liên quan đến độ độc của thực vật, nếu có.")
    private List<String> toxicity;

    @Field(type = FieldType.Keyword)
    @Schema(description = "Các vị trí trong vườn nơi thực vật có thể được trồng.")
    private List<String> gardenLocations;

    @Field(type = FieldType.Keyword)
    @Schema(description = "Các phong cách vườn phù hợp với thực vật (ví dụ: vườn cổ điển, vườn hiện đại, v.v.).")
    private List<String> gardenStyles;

    @Field(type = FieldType.Text, analyzer = "standard")
    @Schema(description = "Mô tả chi tiết về thực vật, bao gồm các đặc điểm nổi bật, cách chăm sóc, và các thông tin hữu ích khác.")
    private String description;
}
