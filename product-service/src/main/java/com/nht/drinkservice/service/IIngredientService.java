package com.nht.drinkservice.service;

import com.nht.drinkservice.dto.IngredientDto;
import com.nht.drinkservice.dto.controller.request.RequestCreateIngredient;
import com.nht.drinkservice.dto.controller.request.RequestUpdateIngredient;

import java.util.List;

public interface IIngredientService {
    IngredientDto createIngredient(RequestCreateIngredient ingredient);

    List<IngredientDto> getAllIngredients();

    IngredientDto findById(Long id);

    IngredientDto updateIngredient(Long id, RequestUpdateIngredient ingredient);

    void deleteIngredient(Long id);

}
