package com.nht.drinkservice.dto.controller.request;

import java.util.List;

public record RequestCreateProduct(
        String name,
        String imageUrl,
        Long categoryId,
        Double price,
        List<Long> ingredients,
        Integer quantity) {
}
