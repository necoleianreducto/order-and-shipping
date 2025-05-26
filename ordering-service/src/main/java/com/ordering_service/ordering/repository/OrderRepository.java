package com.ordering_service.ordering.repository;

import com.ordering_service.ordering.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByOrderNumber(String orderNumber);
}
