package com.example.rohlik.services;

import com.example.rohlik.models.ItemInOrder;
import com.example.rohlik.models.MissingItemDTO;
import com.example.rohlik.models.Order;
import com.example.rohlik.models.ProductOrderDTO;
import com.example.rohlik.repositories.ItemInOrderRepository;
import com.example.rohlik.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ItemInOrderRepository itemInOrderRepository;
    private final ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ItemInOrderRepository itemInOrderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.itemInOrderRepository = itemInOrderRepository;
        this.productService = productService;
        Timer timer = new Timer();
        timer.schedule(task, 0, 1000L * 60L);
    }


    @Override
    public ResponseEntity<Object> createOrder(List<ProductOrderDTO> productsOrderDTO) {
        List<MissingItemDTO> outOfStock = new ArrayList<>();
        List<ItemInOrder> itemsInOrder = createItemsInOrder(productsOrderDTO);

        for (ItemInOrder item : itemsInOrder) {
            if (item.getQuantity() > item.getProduct().getQuantityInStock()) {
                long missingCount = item.getQuantity() - item.getProduct().getQuantityInStock();
                MissingItemDTO missingItemDTO = new MissingItemDTO(item.getProduct(), missingCount);
                outOfStock.add(missingItemDTO);
            }
        }

        if (outOfStock.isEmpty()) {
            for (ItemInOrder item : itemsInOrder) {
                productService.updateProductQuantityInStock(item.getProduct(), item.getQuantity());
                itemInOrderRepository.save(item);
            }
            Order order = new Order(itemsInOrder);
            orderRepository.save(order);
            for (ItemInOrder item : itemsInOrder) {
                item.setOrder(order);
                itemInOrderRepository.save(item);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(itemsInOrder);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(outOfStock);
        }
    }

    @Override
    public ResponseEntity<Object> cancelOrder(long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            List<ItemInOrder> itemsInOrder = itemInOrderRepository.findAllByOrder_Id(id);
            for (ItemInOrder item : itemsInOrder) {
                productService.findProductByName(item.getProduct().getName()).setQuantityInStock(productService.findProductByName(item.getProduct().getName()).getQuantityInStock() + item.getQuantity());
            }
            orderRepository.delete(order.get());
            return ResponseEntity.status(HttpStatus.OK).body("The order was cancelled.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This order does not exist.");
    }

    @Override
    public ResponseEntity<Object> paymentOrder(long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            if (order.get().isPayment()) {
                return ResponseEntity.status(HttpStatus.OK).body("Payment was already made.");
            } else {
                order.get().setPayment(true);
                orderRepository.save(order.get());
                return ResponseEntity.status(HttpStatus.OK).body("Payment done");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This order does not exist.");
    }

    @Override
    public List<ItemInOrder> createItemsInOrder(List<ProductOrderDTO> productsOrderDTO) {
        List<ItemInOrder> itemsInOrder = new ArrayList<>();
        for (ProductOrderDTO product : productsOrderDTO) {
            itemsInOrder.add(new ItemInOrder(productService.findProductByName(product.getName()), product.getCount()));
        }
        return itemsInOrder;
    }


    TimerTask task = new TimerTask() {
        public void run() {
            Date currentDate = new Date(System.currentTimeMillis() - 1000 * 60 * 30);
            List<Order> orderList = orderRepository.findAllByPaymentAndCreationDateBefore(false, currentDate);

            for (Order order : orderList) {
                cancelOrder(order.getId());
                System.out.println("Order with id " + order.getId() + " was cancelled");
            }
            System.out.println(currentDate + " This is current date");
        }
    };
}
