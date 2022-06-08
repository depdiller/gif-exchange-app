package com.alfa.exchangerate.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class FeignExceptionObject {
    private final String error = "Feign Exception";
    private String content;
    private String message;
    private HttpStatus status;

    public FeignExceptionObject(String content, String message, HttpStatus status) {
        this.content = content;
        this.message = message;
        this.status = status;
    }
}
