package com.example.rohlik.services;

import com.example.rohlik.models.ItemInOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {
    ResponseEntity<Object> createOrder(List<ItemInOrder> itemsInOrder);
    ResponseEntity<Object> cancelOrder(long id);
    ResponseEntity<Object> paymentOrder(long id);
}
