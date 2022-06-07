package com.alfa.exchangerate.config;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {
    private String code;
    private Object value;

    @JsonAnySetter
    public void setCode(String name, Object value) {
        code = name;
        this.value = value;
    }
}
