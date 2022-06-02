package com.example.rohlik.services;

import com.example.rohlik.models.ItemInOrder;
import com.example.rohlik.models.MissingItemDTO;
import com.example.rohlik.models.Order;
import com.example.rohlik.repositories.ItemInOrderRepository;
import com.example.rohlik.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final ItemInOrderRepository itemInOrderRepository;
    private final ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ItemInOrderRepository itemInOrderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.itemInOrderRepository = itemInOrderRepository;
        this.productService = productService;
    }


    @Override
    public ResponseEntity<Object> createOrder(List<ItemInOrder> itemsInOrder) {
        List<MissingItemDTO> outOfStock = new ArrayList<>();

        for (ItemInOrder item: itemsInOrder) {
            if (item.getQuantity() > item.getProduct().getQuantityInStock()){
                long missingCount = item.getQuantity() - item.getProduct().getQuantityInStock();
                MissingItemDTO missingItemDTO = new MissingItemDTO(item.getProduct(), missingCount);
                outOfStock.add(missingItemDTO);
            }
        }

        if (outOfStock.isEmpty()){
            for (ItemInOrder item: itemsInOrder) {
                productService.updateProductQuantityInStock(item.getProduct(), item.getQuantity());
                itemInOrderRepository.save(item);
            }
            orderRepository.save(new Order(itemsInOrder));
            return ResponseEntity.status(HttpStatus.CREATED).body(itemsInOrder);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(outOfStock);
        }
    }

    @Override
    public ResponseEntity<Object> cancelOrder(long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()){
            orderRepository.delete(order.get());
            return ResponseEntity.status(HttpStatus.OK).body("The order was cancelled.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This order does not exist.");
    }

    @Override
    public ResponseEntity<Object> paymentOrder(long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()){
            if (order.get().isPayment()){
                return ResponseEntity.status(HttpStatus.OK).body("Payment was already made.");
            } else{
                order.get().setPayment(true);
                orderRepository.save(order.get());
                return ResponseEntity.status(HttpStatus.OK).body("Payment done");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This order does not exist.");
    }


}
