package com.example.rohlik.repositories;

import com.example.rohlik.models.ItemInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInOrderRepository extends JpaRepository<ItemInOrder, Long> {
}
