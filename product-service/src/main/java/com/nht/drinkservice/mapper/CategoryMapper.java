package com.nht.drinkservice.mapper;

import com.nht.drinkservice.dto.CategoryDto;
import com.nht.drinkservice.dto.controller.request.RequestCreateCategory;
import com.nht.drinkservice.dto.controller.request.RequestUpdateCategory;
import com.nht.drinkservice.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);
    List<CategoryDto> toDtoList(List<Category> categories);
    List<Category> toEntityList(List<CategoryDto> categoryDtos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toEntity(RequestCreateCategory requestCreateCategory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateEntityFromDto(@MappingTarget Category category, RequestUpdateCategory requestUpdateCategory);
}
