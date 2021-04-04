package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.CategoryElementDao;
import com.dotin.timeOffRequest.dto.CategoryElementDto;
import com.dotin.timeOffRequest.entity.CategoryElement;
import com.dotin.timeOffRequest.mapper.CategoryElementMapper;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
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
        categoryElementDao.openCurrentSessionWithTransaction();
        CategoryElement categoryElement = categoryElementMapper.toEntity(categoryElementDto);
        categoryElementDao.insert(categoryElement);
        categoryElementDao.closeCurrentSessionWithTransaction();
    }

    public void update(CategoryElementDto categoryElementDto) {
        log.info("object with below info for update has received : " + categoryElementDto.getName());
        categoryElementDao.openCurrentSessionWithTransaction();
        categoryElementDao.update(categoryElementMapper.toEntity(categoryElementDto));
        categoryElementDao.closeCurrentSessionWithTransaction();
    }

    public CategoryElementDto findById(Long id) {
        log.info("request with below id for find has received : " + id);
        categoryElementDao.openCurrentSessionWithTransaction();
        CategoryElement categoryElement = categoryElementDao.getEntity(id);
        categoryElementDao.closeCurrentSessionWithTransaction();
        return categoryElementMapper.toDto(categoryElement);
    }

    public CategoryElementDto findByCode(Long code) {
        log.info("request with below code for find has received : " + code);
        categoryElementDao.openCurrentSessionWithTransaction();
        CategoryElement categoryElement = categoryElementDao.findByCode(code);
        categoryElementDao.closeCurrentSessionWithTransaction();
        return categoryElementMapper.toDto(categoryElement);
    }

    public void delete(Long id) {
        log.info("request with below id for delete has received : " + id);
        categoryElementDao.openCurrentSessionWithTransaction();
        CategoryElement categoryElement = categoryElementDao.getEntity(id);
        categoryElement.setActive(false);
        categoryElement.setDisabled(true);
        categoryElementDao.closeCurrentSessionWithTransaction();
    }

    public List<CategoryElementDto> findAll() {
        log.info("request for find all has received");
        categoryElementDao.openCurrentSessionWithTransaction();
        List<CategoryElement> categoryElementList = categoryElementDao.selectAll();
        categoryElementDao.closeCurrentSessionWithTransaction();
        List<CategoryElementDto> categoryElementDtoList = new ArrayList<>();
        for (CategoryElement categoryElement : categoryElementList) {
            categoryElementDtoList.add(categoryElementMapper.toDto(categoryElement));
        }
        return categoryElementDtoList;
    }

    public List<CategoryElementDto> findAllByCode(String string, Long id) {
        log.info("request for find all by below id has received : " + id);
        Criteria criteria = categoryElementDao.openCurrentSessionWithTransaction().createCriteria(CategoryElement.class);
        criteria.add(Restrictions.eq("active", true));
        criteria.add(Restrictions.eq(string, id));
        List<CategoryElement> categoryElementList = criteria.list();
        categoryElementDao.closeCurrentSessionWithTransaction();
        List<CategoryElementDto> categoryElementDtoList = new ArrayList<>();
        for (CategoryElement categoryElement : categoryElementList) {
            categoryElementDtoList.add(categoryElementMapper.toDto(categoryElement));
        }
        return categoryElementDtoList;
    }
}

