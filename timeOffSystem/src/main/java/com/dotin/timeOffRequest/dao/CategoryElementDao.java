package com.dotin.timeOffRequest.dao;

import com.dotin.timeOffRequest.entity.CategoryElement;

public class CategoryElementDao extends GenericDaoImpl<CategoryElement, Long> {

    public CategoryElement findByCode(Long code) {
        return (CategoryElement) getCurrentSession()
                .createQuery("FROM CategoryElement ce WHERE ce.code = :code")
                .setParameter("code", code)
                .uniqueResult();
    }
}
