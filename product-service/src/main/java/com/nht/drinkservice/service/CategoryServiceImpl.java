package com.nht.drinkservice.service;

import com.nht.drinkservice.dto.CategoryDto;
import com.nht.drinkservice.dto.controller.request.RequestCreateCategory;
import com.nht.drinkservice.dto.controller.request.RequestUpdateCategory;
import com.nht.drinkservice.mapper.CategoryMapper;
import com.nht.drinkservice.repository.CategoryRepository;
import exceptions.AlreadyExistsException;
import exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto addCategory(RequestCreateCategory requestCreateCategory) {
        if (categoryRepository.existsByName(requestCreateCategory.name())) {
            throw new AlreadyExistsException("Category with the given name already exists: " + requestCreateCategory.name());
        }

        var category = categoryMapper.toEntity(requestCreateCategory);
        var savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
    }

    @Override
    public List<CategoryDto> findAll() {
        var categories = categoryRepository.findAll();
        return categoryMapper.toDtoList(categories);
    }

    @Override
    public List<CategoryDto> findByNameContaining(String name) {
        var categories = categoryRepository.findByNameContaining(name);
        return categoryMapper.toDtoList(categories);
    }

    @Override
    public CategoryDto updateCategory(Long id, RequestUpdateCategory requestUpdateCategory) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

        if (categoryRepository.existsByName(requestUpdateCategory.name())) {
            throw new AlreadyExistsException("Category with the given name already exists: " + requestUpdateCategory.name());
        }

        categoryMapper.updateEntityFromDto(category, requestUpdateCategory);
        var updatedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

        categoryRepository.delete(category);
    }
}
