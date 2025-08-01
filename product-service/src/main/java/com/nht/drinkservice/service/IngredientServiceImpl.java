package com.nht.drinkservice.service;

import com.nht.drinkservice.dto.IngredientDto;
import com.nht.drinkservice.dto.controller.request.RequestCreateIngredient;
import com.nht.drinkservice.dto.controller.request.RequestUpdateIngredient;
import com.nht.drinkservice.mapper.IngredientMapper;
import com.nht.drinkservice.repository.IngredientRepository;
import exceptions.AlreadyExistsException;
import exceptions.NotFoundException;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IIngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    public IngredientDto createIngredient(RequestCreateIngredient ingredient) {
        // check if ingredient name already exists
        if (isIngredientExists(ingredient.name())) {
            throw new AlreadyExistsException("Ingredient with the given name already exists: " + ingredient.name());
        }
        return ingredientMapper.toDto(ingredientRepository.save(ingredientMapper.toEntity(ingredient)));
    }

    private boolean isIngredientExists(@NotBlank String name) {
        return ingredientRepository.findByName(name).isPresent();
    }

    @Override
    public List<IngredientDto> getAllIngredients() {
        return ingredientMapper.toDtoList(ingredientRepository.findAll());
    }

    @Override
    public IngredientDto findById(Long id) {
        return ingredientRepository.findById(id)
                .map(ingredientMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Ingredient not found: " + id));
    }

    @Override
    public IngredientDto updateIngredient(Long id, RequestUpdateIngredient ingredient) {
        // check if ingredient exists
        final var ingredientToUpdate = ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ingredient not found: " + id));

        // check if ingredient name already exists
        if (isIngredientExists(ingredient.name())) {
            throw new AlreadyExistsException("Ingredient with the given name already exists: " + ingredient.name());
        }

        ingredientMapper.updateEntityFromDto(ingredientToUpdate, ingredient);
        return ingredientMapper.toDto(ingredientRepository.save(ingredientToUpdate));
    }

    @Override
    public void deleteIngredient(Long id) {
        // check if ingredient exists
        if (!ingredientRepository.existsById(id)) {
            throw new NotFoundException("Ingredient not found: " + id);
        }
        ingredientRepository.deleteById(id);
    }
}
