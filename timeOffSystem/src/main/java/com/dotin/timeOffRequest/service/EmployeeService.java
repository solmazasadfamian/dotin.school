package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.EmployeeDao;
import com.dotin.timeOffRequest.entity.Employee;

import java.util.List;

public class EmployeeService {

    private static EmployeeDao employeeDao;

    public EmployeeService() {
        employeeDao = new EmployeeDao();
    }

    public void add(Employee entity) {
        employeeDao.openCurrentSessionWithTransaction();
        employeeDao.insert(entity);
        employeeDao.closeCurrentSessionWithTransaction();
    }

    public void update(Employee entity) {
        employeeDao.openCurrentSessionWithTransaction();
        Employee employee = employeeDao.getEntity(entity.getId());
        entity.setVersion(employee.getVersion());
        employeeDao.update(entity);
        employeeDao.closeCurrentSessionWithTransaction();
    }

    public Employee findById(Long id) {
        employeeDao.openCurrentSessionWithTransaction();
        Employee employee = employeeDao.getEntity(id);
        employeeDao.closeCurrentSessionWithTransaction();
        return employee;
    }

    public void delete(Long id) {
        employeeDao.openCurrentSessionWithTransaction();
        Employee employee = employeeDao.getEntity(id);
        employee.setActive(false);
        employee.setDisabled(false);
        employeeDao.closeCurrentSessionWithTransaction();
    }

    public List<Employee> findAll() {
        employeeDao.openCurrentSessionWithTransaction();
        List<Employee> books = employeeDao.selectAll();
        employeeDao.closeCurrentSessionWithTransaction();
        return books;
    }
}

