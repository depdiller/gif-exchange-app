package com.alfa.exchangerate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class GiphyData {
    private Map<String, Image> images;
}

