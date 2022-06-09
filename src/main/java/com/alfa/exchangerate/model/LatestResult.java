package com.alfa.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@JsonIgnoreProperties(value = {"disclaimer", "license"})
public class LatestResult {
    private String timestamp;
    private String base;
    private Map<String, Double> rates;

    public LatestResult(String timestamp, String base, Map<String, Double> rates) {
        this.timestamp = timestamp;
        this.base = base;
        this.rates = rates;
    }
}
