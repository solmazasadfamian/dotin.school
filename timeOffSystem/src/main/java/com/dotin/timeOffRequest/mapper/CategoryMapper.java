package com.dotin.timeOffRequest.mapper;

import com.dotin.timeOffRequest.dto.CategoryDto;
import com.dotin.timeOffRequest.entity.Category;

public class CategoryMapper {

    public Category toEntity(CategoryDto categoryDto){
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setActive(categoryDto.getActive());
        category.setDisabled(categoryDto.getDisabled());
        return category;
    }

    public CategoryDto toDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setActive(category.getActive());
        categoryDto.setDisabled(category.getDisabled());
        return categoryDto;
    }
}
