package com.nht.drinkservice.dto.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RequestUpdateProduct(
        @NotBlank String name,
        @NotBlank String imageUrl,
        @NotNull Long categoryId,
        @NotNull Double price,
        @NotEmpty List<@NotNull Long> ingredients) {
}
