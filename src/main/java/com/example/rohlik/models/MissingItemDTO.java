package com.example.rohlik.models;

public class MissingItemDTO {
    Product product;
    long missingCount;

    public MissingItemDTO(Product product, long missingCount) {
        this.product = product;
        this.missingCount = missingCount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getMissingCount() {
        return missingCount;
    }

    public void setMissingCount(long missingCount) {
        this.missingCount = missingCount;
    }
}
