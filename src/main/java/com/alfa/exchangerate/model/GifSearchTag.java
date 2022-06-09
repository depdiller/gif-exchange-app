package com.alfa.exchangerate.model;

public enum GifSearchTag {
    Broke("broke"),
    Rich("rich");

    private final String text;

    GifSearchTag(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
