package com.alfa.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(value = {"disclaimer", "license"})
public class LatestResult {
    private String timestamp;
    private String base;
    private Map<String, Double> rates;
}
