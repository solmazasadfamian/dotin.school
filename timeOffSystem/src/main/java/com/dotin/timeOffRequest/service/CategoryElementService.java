package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.CategoryElementDao;
import com.dotin.timeOffRequest.dto.CategoryElementDto;
import com.dotin.timeOffRequest.entity.CategoryElement;
import com.dotin.timeOffRequest.mapper.CategoryElementMapper;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class CategoryElementService {
    private final static Logger log = Logger.getLogger(CategoryElementService.class.getName());

    private final CategoryElementDao categoryElementDao;
    private final CategoryElementMapper categoryElementMapper;

    public CategoryElementService() {
        categoryElementDao = new CategoryElementDao();
        categoryElementMapper = new CategoryElementMapper();
    }

    public void add(CategoryElementDto categoryElementDto) {
        log.info("object with below info for save has received : " + categoryElementDto.getName());
        Session session = categoryElementDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            CategoryElement categoryElement = categoryElementMapper.toEntity(categoryElementDto);
            categoryElementDao.insert(categoryElement);
            transaction.commit();
        } finally {
            session.close();
        }
    }

    public void update(CategoryElementDto categoryElementDto) {
        log.info("object with below info for update has received : " + categoryElementDto.getName());
        Session session = categoryElementDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            categoryElementDao.update(categoryElementMapper.toEntity(categoryElementDto));
            transaction.commit();
        } finally {
            session.close();
        }
    }

    public CategoryElementDto findById(Long id) {
        log.info("request with below id for find has received : " + id);
        Session session = categoryElementDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            CategoryElement categoryElement = categoryElementDao.getEntity(id);
            transaction.commit();
            return categoryElementMapper.toDto(categoryElement);
        } finally {
            session.close();
        }
    }

    public CategoryElementDto findByCode(Long code) {
        log.info("request with below code for find has received : " + code);
        Session session = categoryElementDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            CategoryElement categoryElement = categoryElementDao.findByCode(code);
            transaction.commit();
            return categoryElementMapper.toDto(categoryElement);
        } finally {
            session.close();
        }
    }

    public void delete(Long id) {
        log.info("request with below id for delete has received : " + id);
        Session session = categoryElementDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            CategoryElement categoryElement = categoryElementDao.getEntity(id);
            categoryElement.setActive(false);
            categoryElement.setDisabled(true);
            transaction.commit();
        } finally {
            session.close();
        }
    }

    public List<CategoryElementDto> findAll() {
        log.info("request for find all has received");
        Session session = categoryElementDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            List<CategoryElement> categoryElementList = categoryElementDao.selectAll();
            List<CategoryElementDto> categoryElementDtoList = new ArrayList<>();
            for (CategoryElement categoryElement : categoryElementList) {
                categoryElementDtoList.add(categoryElementMapper.toDto(categoryElement));
            }
            transaction.commit();
            return categoryElementDtoList;
        } finally {
            session.close();
        }
    }

    public List<CategoryElementDto> findAllByCode(String string, Long id) {
        log.info("request for find all by below id has received : " + id);
        Session session = categoryElementDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(CategoryElement.class);
            criteria.add(Restrictions.eq("active", true));
            criteria.add(Restrictions.eq(string, id));
            List<CategoryElement> categoryElementList = criteria.list();
            List<CategoryElementDto> categoryElementDtoList = new ArrayList<>();
            for (CategoryElement categoryElement : categoryElementList) {
                categoryElementDtoList.add(categoryElementMapper.toDto(categoryElement));
            }
            transaction.commit();
            return categoryElementDtoList;
        } finally {
            session.close();
        }
    }
}

