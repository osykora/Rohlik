package com.example.rohlik.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private Integer quantityInStock;
    private Integer pricePerUnit;

    // is it mandatory to have parameterless ctor?
    public Product() {
    }

    public Product(String name, Integer quantityInStock, Integer pricePerUnit) {
        this.name = name;
        this.quantityInStock = quantityInStock;
        this.pricePerUnit = pricePerUnit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Integer getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Integer pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
}
