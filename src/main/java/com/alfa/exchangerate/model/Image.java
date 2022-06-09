package com.alfa.exchangerate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class Image {
    private String height;
    private String size;
    private String url;
    private String width;
}
