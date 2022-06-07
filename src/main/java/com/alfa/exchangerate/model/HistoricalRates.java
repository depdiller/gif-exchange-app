package com.alfa.exchangerate.model;

import com.alfa.exchangerate.config.Rate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class HistoricalRates {
    private String disclaimer;
    private String license;
    private String timestamp;
    private String base;

    @JsonProperty("rates")
    Rate rate;
}
