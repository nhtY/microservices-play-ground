package com.nht.orderservice.dto;

import com.nht.orderservice.entity.Order;

public record OrderLineDto(
        Long id,
        Order order,
        Long productId, // Logical reference to Product
        String productName,
        Integer quantity,
        Double pricePerUnit) {
}
