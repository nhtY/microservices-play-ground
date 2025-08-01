package com.nht.drinkservice.dto;

public record IngredientDto(
        Long id,
        String name,
        ProductDto product) {
}
