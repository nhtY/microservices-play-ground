package com.nht.drinkservice.repository;

import com.nht.drinkservice.entity.Ingredient;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByName(@NotBlank String name);
}
