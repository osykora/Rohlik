package com.example.rohlik.controllers;

import com.example.rohlik.models.ProductOrderDTO;
import com.example.rohlik.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    // Do you know Lombok?
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    // Do you know Swagger?
    // Why is response an Object?
    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody List<ProductOrderDTO> productsOrderDTO){
        return orderService.createOrder(productsOrderDTO);
    }

    // What happens when an exception is thrown?
    @GetMapping("/cancel/{id}")
    public ResponseEntity<Object> cancelOrder(@PathVariable long id){
        return orderService.cancelOrder(id);
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<Object> paymentOrder(@PathVariable long id){
        return orderService.paymentOrder(id);
    }

}
