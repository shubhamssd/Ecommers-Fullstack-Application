package com.eCommers.eCommersApp.repo;

import com.eCommers.eCommersApp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
