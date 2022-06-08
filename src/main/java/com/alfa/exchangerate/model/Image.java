package com.alfa.exchangerate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Image {
    private String height;
    private String size;
    private String url;
    private String width;
}
