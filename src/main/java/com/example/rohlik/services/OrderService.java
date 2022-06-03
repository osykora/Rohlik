package com.example.rohlik.services;

import com.example.rohlik.models.ItemInOrder;
import com.example.rohlik.models.ProductOrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {
    ResponseEntity<Object> createOrder(List<ProductOrderDTO> productsOrderDTO);
    ResponseEntity<Object> cancelOrder(long id);
    ResponseEntity<Object> paymentOrder(long id);
    List<ItemInOrder> createItemsInOrder(List<ProductOrderDTO> productsOrderDTO);
}
