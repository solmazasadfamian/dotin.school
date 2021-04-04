package com.dotin.timeOffRequest.mapper;

import com.dotin.timeOffRequest.dao.CategoryDao;
import com.dotin.timeOffRequest.dto.CategoryElementDto;
import com.dotin.timeOffRequest.entity.Category;
import com.dotin.timeOffRequest.entity.CategoryElement;

public class CategoryElementMapper {
    private final CategoryDao categoryDao;

    public CategoryElementMapper() {
        this.categoryDao = new CategoryDao();
    }

    public CategoryElement toEntity(CategoryElementDto categoryElementDto) {
        CategoryElement categoryElement = new CategoryElement();
        categoryElement.setId(categoryElementDto.getId());
        categoryElement.setCode(categoryElementDto.getCode());
        categoryElement.setDisabled(categoryElementDto.getDisabled());
        categoryElement.setActive(categoryElementDto.getActive());
        categoryElement.setName(categoryElementDto.getName());
        if (categoryElementDto.getCategoryId() != null) {
            categoryDao.openCurrentSession();
            Category category = categoryDao.getEntity(categoryElementDto.getCategoryId());
            categoryDao.closeCurrentSession();
            categoryElement.setCategory(category);
        }
        return categoryElement;
    }

    public CategoryElementDto toDto(CategoryElement categoryElement) {
        CategoryElementDto categoryElementDto = new CategoryElementDto();
        categoryElementDto.setId(categoryElement.getId());
        categoryElementDto.setActive(categoryElement.getActive());
        categoryElementDto.setCategoryId(categoryElement.getCategory() != null ? categoryElement.getCategory().getId() : null);
        categoryElementDto.setCode(categoryElement.getCode());
        categoryElementDto.setDisabled(categoryElement.getDisabled());
        categoryElementDto.setName(categoryElement.getName());
        return categoryElementDto;
    }
}
