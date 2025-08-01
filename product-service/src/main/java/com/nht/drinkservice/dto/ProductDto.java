package com.nht.drinkservice.dto;

import java.util.List;

public record ProductDto(
        Long id,
        String name,
        String imageUrl,
        CategoryDto category,
        Double price,
        List<IngredientDto> ingredients,
        InventoryDto inventory) {
}
