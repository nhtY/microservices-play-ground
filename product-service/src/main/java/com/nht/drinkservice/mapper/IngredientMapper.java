package com.nht.drinkservice.mapper;

import com.nht.drinkservice.dto.IngredientDto;
import com.nht.drinkservice.dto.controller.request.RequestCreateIngredient;
import com.nht.drinkservice.dto.controller.request.RequestUpdateIngredient;
import com.nht.drinkservice.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
     IngredientDto toDto(Ingredient ingredient);
     Ingredient toEntity(IngredientDto ingredientDto);
     List<IngredientDto> toDtoList(List<Ingredient> ingredients);
     List<Ingredient> toEntityList(List<IngredientDto> ingredientDtos);

     @Mapping(target = "id", ignore = true)
     @Mapping(target = "product", ignore = true)
     Ingredient toEntity(RequestCreateIngredient ingredient);

     @Mapping(target = "id", ignore = true)
     @Mapping(target = "product", ignore = true)
     void updateEntityFromDto(@MappingTarget Ingredient ingredient, RequestUpdateIngredient requestUpdateIngredient);
}
