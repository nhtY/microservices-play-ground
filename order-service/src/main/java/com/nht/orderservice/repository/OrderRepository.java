package com.nht.orderservice.repository;

import com.nht.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOwnerId(Long ownerId);
}
