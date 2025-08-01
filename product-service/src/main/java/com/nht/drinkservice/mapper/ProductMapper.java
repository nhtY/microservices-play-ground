package com.nht.drinkservice.mapper;

import com.nht.drinkservice.dto.ProductDto;
import com.nht.drinkservice.dto.controller.request.RequestCreateProduct;
import com.nht.drinkservice.dto.controller.request.RequestUpdateProduct;
import com.nht.drinkservice.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);
    List<ProductDto> toDtoList(List<Product> products);
    List<Product> toEntityList(List<ProductDto> productDtos);

    // Create mapper: ignore the id, Category and Ingredients because they are set in the service.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "ingredients", ignore = true)
    @Mapping(target = "inventory", ignore = true)
    Product toEntity(RequestCreateProduct requestCreateProduct);

    // Update the entity on hand: ignore the id, Category and Ingredients because they are set in the service.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true) // will be set manually
    @Mapping(target = "ingredients", ignore = true) // will be set manually
    @Mapping(target = "inventory", ignore = true) // will be set manually
    void updateEntity(@MappingTarget Product existingProduct, RequestUpdateProduct requestUpdateProduct);
}
