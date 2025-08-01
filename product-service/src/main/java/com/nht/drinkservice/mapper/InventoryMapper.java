package com.nht.drinkservice.mapper;

import com.nht.drinkservice.dto.InventoryDto;
import com.nht.drinkservice.entity.Inventory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
     InventoryDto toDto(Inventory inventory);
     Inventory toEntity(InventoryDto inventoryDto);
     List<InventoryDto> toDtoList(List<Inventory> inventories);
     List<Inventory> toEntityList(List<InventoryDto> inventoryDtos);
}
