package com.nht.orderservice.dto.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RequestCreateOrderLine(
        @NotNull Long productId, // Logical reference to Product
        @NotNull @Min(1) Integer quantity) {
}
