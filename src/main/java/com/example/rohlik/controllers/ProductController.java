package com.example.rohlik.controllers;

import com.example.rohlik.models.Product;
import com.example.rohlik.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Product is db entity - how would you it differently
    @PostMapping("/create")
    public ResponseEntity<Object> createProduct(@RequestBody Product product){
        return productService.createProduct(product.getName(), product.getQuantityInStock(), product.getPricePerUnit());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable long id){
        return productService.deleteProduct(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable long id, @RequestBody Product product){
        return productService.updateProduct(id, product);
    }
}
