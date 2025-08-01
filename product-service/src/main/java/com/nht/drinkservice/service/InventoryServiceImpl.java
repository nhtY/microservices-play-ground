package com.nht.drinkservice.service;

import com.nht.drinkservice.dto.InventoryDto;
import com.nht.drinkservice.mapper.InventoryMapper;
import com.nht.drinkservice.repository.InventoryRepository;
import exceptions.InvalidDataException;
import exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements IInventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryDto findById(Long id) {
        return inventoryRepository.findById(id)
                .map(inventoryMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Inventory not found: " + id));
    }

    @Override
    public InventoryDto getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId)
                .map(inventoryMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Inventory not found for product: " + productId));
    }

    @Override
    public InventoryDto increaseQuantityOnHand(Long productId, Integer quantityOnHand) {
        return inventoryRepository.findByProductId(productId)
                .map(inventory -> {
                    inventory.setQuantityOnHand(inventory.getQuantityOnHand() + quantityOnHand);
                    return inventoryMapper.toDto(inventoryRepository.save(inventory));
                })
                .orElseThrow(() -> new NotFoundException("Inventory not found for product: " + productId));
    }

    @Override
    public InventoryDto decreaseQuantityOnHand(Long productId, Integer quantityOnHand) {
        return inventoryRepository.findByProductId(productId)
                .map(inventory -> {
                    if (inventory.getQuantityOnHand() < quantityOnHand) {
                        throw new InvalidDataException("Not enough quantity on hand for product: " + productId);
                    }
                    inventory.setQuantityOnHand(inventory.getQuantityOnHand() - quantityOnHand);
                    return inventoryMapper.toDto(inventoryRepository.save(inventory));
                })
                .orElseThrow(() -> new NotFoundException("Inventory not found for product: " + productId));
    }

    @Override
    public void deleteInventory(Long productId) {
        inventoryRepository.findByProductId(productId)
                .ifPresentOrElse(inventoryRepository::delete, () -> {
                    throw new NotFoundException("Inventory not found for product: " + productId);
                });
    }
}
