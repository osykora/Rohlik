package com.example.rohlik.services;

import com.example.rohlik.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<Object>  createProduct(String name, Integer quantityInStock, Integer pricePerUnit);
    void updateProductQuantityInStock(Product product, int quantity);
    ResponseEntity<Object> deleteProduct(long id);
    ResponseEntity<Object> updateProduct(long id, Product product);
    Product findProductByName(String name);

}
