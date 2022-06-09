package com.alfa.exchangerate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class GiphyData {
    private Map<String, Image> images;
}

