package com.nht.orderservice.entity;

public enum OrderStatus {
    NEW,
    VALIDATED,
    VALIDATION_ERROR,
    PENDING_INVENTORY,
    ALLOCATED,
    ALLOCATION_ERROR,
    PICKED_UP,
    DELIVERY_ERROR,
    DELIVERED,
    CANCELLED,
}
