package com.nht.orderservice.dto;

import com.nht.orderservice.entity.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        Long ownerId,
        LocalDateTime orderDate,
        OrderStatus status,
        Double totalAmount,
        List<OrderLineDto> orderLinesDtos) {
}
