package com.alfa.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Dictionary;

@Data
@JsonIgnoreProperties(value = {"disclaimer", "license"})
public class HistoricalRates {
    private String timestamp;
    private String base;

    @JsonProperty("rates")
    Rate rate;
    public HistoricalRates(String timestamp, String base, Rate rate) {
        this.timestamp = timestamp;
        this.base = base;
        this.rate = rate;
    }
}
