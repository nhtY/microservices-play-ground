package com.nht.drinkservice.service;

import com.nht.drinkservice.dto.InventoryDto;

public interface IInventoryService {
    InventoryDto findById(Long id);

    InventoryDto getInventoryByProductId(Long productId);

    InventoryDto increaseQuantityOnHand(Long productId, Integer quantityOnHand);

    InventoryDto decreaseQuantityOnHand(Long productId, Integer quantityOnHand);

    void deleteInventory(Long productId);
}
