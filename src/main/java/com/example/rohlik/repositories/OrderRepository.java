package com.example.rohlik.repositories;

import com.example.rohlik.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByPaymentAndCreationDateBefore(boolean payment, Date date);
}
