package com.elastic.plant.model.subfield;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dimension {
    private Float min;
    private Float max;
    private String unit;
}
