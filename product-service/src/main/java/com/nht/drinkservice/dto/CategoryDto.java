package com.nht.drinkservice.dto;

import com.nht.drinkservice.entity.Product;

import java.util.List;

public record CategoryDto(
        Long id,
        String name,
        List<ProductDto> products) {
}
