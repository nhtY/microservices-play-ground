package com.nht.drinkservice.dto;

public record InventoryDto(
        Long id,
        Integer quantityOnHand,
        ProductDto product) {
}
