package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.CategoryElementDao;
import com.dotin.timeOffRequest.entity.CategoryElement;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class CategoryElementService {
    private final static Logger log = Logger.getLogger(CategoryElementService.class.getName());

    private CategoryElementDao categoryElementDao;

    public CategoryElementService() {
        categoryElementDao = new CategoryElementDao();
    }

    public void add(CategoryElement categoryElement) {
        log.info("object with below info for save has received : " + categoryElement.getName());
        categoryElementDao.openCurrentSessionWithTransaction();
        categoryElementDao.insert(categoryElement);
        categoryElementDao.closeCurrentSessionWithTransaction();
    }

    public void update(CategoryElement categoryElement) {
        log.info("object with below info for update has received : " + categoryElement.getName());
        categoryElementDao.openCurrentSessionWithTransaction();
        categoryElementDao.update(categoryElement);
        categoryElementDao.closeCurrentSessionWithTransaction();
    }

    public CategoryElement findById(Long id) {
        log.info("request with below id for find has received : " + id);
        categoryElementDao.openCurrentSessionWithTransaction();
        CategoryElement categoryElement = categoryElementDao.getEntity(id);
        categoryElementDao.closeCurrentSessionWithTransaction();
        return categoryElement;
    }

    public CategoryElement findByCode(Long code) {
        log.info("request with below code for find has received : " + code);
        categoryElementDao.openCurrentSessionWithTransaction();
        CategoryElement categoryElement = categoryElementDao.findByCode(code);
        categoryElementDao.closeCurrentSessionWithTransaction();
        return categoryElement;
    }

    public void delete(Long id) {
        log.info("request with below id for delete has received : " + id);
        categoryElementDao.openCurrentSessionWithTransaction();
        CategoryElement categoryElement = categoryElementDao.getEntity(id);
        categoryElement.setActive(false);
        categoryElement.setDisabled(true);
        categoryElementDao.closeCurrentSessionWithTransaction();
    }

    public List<CategoryElement> findAll() {
        log.info("request for find all has received");
        categoryElementDao.openCurrentSessionWithTransaction();
        List<CategoryElement> categoryElementList = categoryElementDao.selectAll();
        categoryElementDao.closeCurrentSessionWithTransaction();
        return categoryElementList;
    }

    public List<CategoryElement> findAllByCode(String string, Long id) {
        log.info("request for find all by below id has received : " + id);
        Criteria criteria = categoryElementDao.openCurrentSessionWithTransaction().createCriteria(CategoryElement.class);
        criteria.add(Restrictions.eq("active", true));
        criteria.add(Restrictions.eq(string, id));
        List<CategoryElement> categoryElementList = criteria.list();
        categoryElementDao.closeCurrentSessionWithTransaction();
        return categoryElementList;
    }
}

