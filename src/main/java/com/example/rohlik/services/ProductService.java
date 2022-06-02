package com.example.rohlik.services;

import com.example.rohlik.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    ResponseEntity<Object>  createProduct(String name, long quantityInStock, int pricePerUnit);
    void updateProductQuantityInStock(Product product, int quantity);
    ResponseEntity<Object> deleteProduct(long id);
    ResponseEntity<Object> updateProduct(long id, Product product);

}
