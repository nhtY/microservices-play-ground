package com.nht.drinkservice.repository;

import com.nht.drinkservice.entity.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    boolean existsByName(@NotBlank String name);

    List<Category> findByNameContaining(String name);
}
