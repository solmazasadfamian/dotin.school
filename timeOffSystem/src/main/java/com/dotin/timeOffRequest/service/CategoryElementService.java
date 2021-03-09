package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.CategoryElementDao;
import com.dotin.timeOffRequest.entity.CategoryElement;

import java.util.List;

public class CategoryElementService {

    private static CategoryElementDao categoryElementDao;

    public CategoryElementService() {
        categoryElementDao = new CategoryElementDao();
    }

    public void add(CategoryElement entity) {
        categoryElementDao.openCurrentSessionWithTransaction();
        categoryElementDao.insert(entity);
        categoryElementDao.closeCurrentSessionWithTransaction();
    }

    public void update(CategoryElement entity) {
        categoryElementDao.openCurrentSessionWithTransaction();
        categoryElementDao.update(entity);
        categoryElementDao.closeCurrentSessionWithTransaction();
    }

    public CategoryElement findById(Long id) {
        categoryElementDao.openCurrentSessionWithTransaction();
        CategoryElement categoryElement = categoryElementDao.getEntity(id);
        categoryElementDao.closeCurrentSessionWithTransaction();
        return categoryElement;
    }

    public void delete(Long id) {
        categoryElementDao.openCurrentSessionWithTransaction();
        CategoryElement categoryElement = categoryElementDao.getEntity(id);
        categoryElement.setActive(false);
        categoryElement.setDisabled(false);
        categoryElementDao.closeCurrentSessionWithTransaction();
    }

    public List<CategoryElement> findAll() {
        categoryElementDao.openCurrentSessionWithTransaction();
        List<CategoryElement> categoryElements = categoryElementDao.selectAll();
        categoryElementDao.closeCurrentSessionWithTransaction();
        return categoryElements;
    }
}

