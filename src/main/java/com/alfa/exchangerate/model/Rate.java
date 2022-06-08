package com.alfa.exchangerate.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {
    private String code;
    private double value;

    @JsonAnySetter
    public void setCode(String name, double value) {
        code = name;
        this.value = value;
    }
}
