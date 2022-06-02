package com.example.rohlik.models;

public class MissingItemDTO {
    Product product;
    long missingCount;

    public MissingItemDTO(Product product, long missingCount) {
        this.product = product;
        this.missingCount = missingCount;
    }
}
