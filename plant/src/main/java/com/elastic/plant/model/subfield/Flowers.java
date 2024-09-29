package com.elastic.plant.model.subfield;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Flowers {
    private List<String> color;
    private List<String> bloomTime;
}
