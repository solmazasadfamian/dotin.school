package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.EmployeeDao;
import com.dotin.timeOffRequest.entity.Employee;
import org.apache.log4j.Logger;

import java.util.List;

public class EmployeeService {
    private final static Logger log = Logger.getLogger(EmployeeService.class.getName());

    private EmployeeDao employeeDao;

    public EmployeeService() {
        employeeDao = new EmployeeDao();
    }

    public void add(Employee employee) {
        log.info("object with below info for save has received : " + employee.getFirstName() + " " + employee.getLastName());
        employeeDao.openCurrentSessionWithTransaction();
        employeeDao.insert(employee);
        employeeDao.closeCurrentSessionWithTransaction();
    }

    public void update(Employee newEmployee) {
        log.info("object with below info for update has received : " + newEmployee.getFirstName() + " " + newEmployee.getLastName());
        employeeDao.openCurrentSessionWithTransaction();
        Employee employee = employeeDao.getEntity(newEmployee.getId());
        newEmployee.setVersion(employee.getVersion());
        employeeDao.update(newEmployee);
        employeeDao.closeCurrentSessionWithTransaction();
    }

    public Employee findById(Long id) {
        log.info("request with below id for find has received : " + id);
        employeeDao.openCurrentSessionWithTransaction();
        Employee employee = employeeDao.getEntity(id);
        employeeDao.closeCurrentSessionWithTransaction();
        return employee;
    }

    public void delete(Long id) {
        log.info("request with below id for delete has received : " + id);
        employeeDao.openCurrentSessionWithTransaction();
        Employee employee = employeeDao.getEntity(id);
        employee.setActive(false);
        employee.setDisabled(true);
        employeeDao.closeCurrentSessionWithTransaction();
    }

    public List<Employee> findAll() {
        log.info("request for find all has received");
        employeeDao.openCurrentSessionWithTransaction();
        List<Employee> employeeList = employeeDao.selectAll();
        employeeDao.closeCurrentSessionWithTransaction();
        return employeeList;
    }
}

