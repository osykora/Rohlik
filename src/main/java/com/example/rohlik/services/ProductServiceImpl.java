package com.example.rohlik.services;

import com.example.rohlik.models.Product;
import com.example.rohlik.repositories.OrderRepository;
import com.example.rohlik.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<Object> createProduct(String name, long quantityInStock, int pricePerUnit) {
        productRepository.save(new Product(name,quantityInStock,pricePerUnit));
        return ResponseEntity.status(HttpStatus.OK).body("Product created.");
    }

    @Override
    public void updateProductQuantityInStock(Product product, int quantity) {
        product.setQuantityInStock(product.getQuantityInStock()-quantity);
        productRepository.save(product);
    }

    @Override
    public ResponseEntity<Object> deleteProduct(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            productRepository.delete(product.get());
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This product does not exist.");
    }

    @Override
    public ResponseEntity<Object> updateProduct(long id, Product updatedProduct) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            product.get().setName(updatedProduct.getName());
            product.get().setPricePerUnit(updatedProduct.getPricePerUnit());
            product.get().setQuantityInStock(updatedProduct.getQuantityInStock());
            productRepository.save(product.get());
            return ResponseEntity.status(HttpStatus.OK).body("Product updated");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This product does not exist.");
    }
}
