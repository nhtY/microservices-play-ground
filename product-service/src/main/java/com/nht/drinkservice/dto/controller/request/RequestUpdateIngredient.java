package com.nht.drinkservice.dto.controller.request;

import jakarta.validation.constraints.NotBlank;

public record RequestUpdateIngredient(
        @NotBlank String name) {
}
