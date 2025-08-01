package com.nht.drinkservice.dto.controller.request;

import jakarta.validation.constraints.NotBlank;

public record RequestCreateIngredient(
        @NotBlank String name) {
}
