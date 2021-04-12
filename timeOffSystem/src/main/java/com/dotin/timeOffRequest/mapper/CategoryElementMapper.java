package com.dotin.timeOffRequest.mapper;

import com.dotin.timeOffRequest.dao.CategoryDao;
import com.dotin.timeOffRequest.dto.CategoryElementDto;
import com.dotin.timeOffRequest.entity.Category;
import com.dotin.timeOffRequest.entity.CategoryElement;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            Session session = categoryDao.openCurrentSession();
            try {
                Transaction transaction = session.beginTransaction();
                Category category = categoryDao.getEntity(categoryElementDto.getCategoryId());
                categoryElement.setCategory(category);
                transaction.commit();
            } finally {
                session.close();
            }
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
