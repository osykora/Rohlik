package com.example.rohlik;

import com.example.rohlik.models.Product;
import com.example.rohlik.repositories.ItemInOrderRepository;
import com.example.rohlik.repositories.OrderRepository;
import com.example.rohlik.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RohlikApplication implements CommandLineRunner {
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private ItemInOrderRepository itemInOrderRepository;

    @Autowired
    public RohlikApplication(ProductRepository productRepository, OrderRepository orderRepository, ItemInOrderRepository itemInOrderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.itemInOrderRepository = itemInOrderRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(RohlikApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        productRepository.save(new Product("mliko", 200,10));
        productRepository.save(new Product("rajce", 250,5));
        productRepository.save(new Product("rohlik", 1000,2));


    }
}
