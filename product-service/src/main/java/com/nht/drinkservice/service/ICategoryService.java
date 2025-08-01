package com.nht.drinkservice.service;

import com.nht.drinkservice.dto.CategoryDto;
import com.nht.drinkservice.dto.controller.request.RequestCreateCategory;
import com.nht.drinkservice.dto.controller.request.RequestUpdateCategory;

import java.util.List;

public interface ICategoryService {
    CategoryDto addCategory(RequestCreateCategory requestCreateCategory);
    CategoryDto findById(Long id);
    List<CategoryDto> findAll();
    List<CategoryDto> findByNameContaining(String name);
    CategoryDto updateCategory(Long id, RequestUpdateCategory requestUpdateCategory);
    void deleteCategory(Long id);
}
