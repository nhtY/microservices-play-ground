package com.nht.orderservice.dto.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RequestCreateOrder(
        @NotNull Long ownerId,
        @NotEmpty List<@NotNull Long> orderLines
) {
}
