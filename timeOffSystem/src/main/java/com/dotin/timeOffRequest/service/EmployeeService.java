package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.EmployeeDao;
import com.dotin.timeOffRequest.dto.EmployeeDto;
import com.dotin.timeOffRequest.entity.Employee;
import com.dotin.timeOffRequest.mapper.EmployeeMapper;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private final static Logger log = Logger.getLogger(EmployeeService.class.getName());

    private final EmployeeDao employeeDao;
    private final EmployeeMapper employeeMapper;

    public EmployeeService() {
        employeeDao = new EmployeeDao();
        employeeMapper = new EmployeeMapper();
    }

    public EmployeeDto add(EmployeeDto employeeDto) {
        log.info("object with below info for save has received : " + employeeDto.getFirstName() + " " + employeeDto.getLastName());
        employeeDao.openCurrentSessionWithTransaction();
        Employee employee = employeeMapper.toEntity(employeeDto);
        Employee saveEmployee = employeeDao.insert(employee);
        employeeDao.closeCurrentSessionWithTransaction();
        return employeeMapper.toDto(saveEmployee);
    }

    public void update(EmployeeDto employeeDto) {
        log.info("object with below info for update has received : " + employeeDto.getFirstName() + " " + employeeDto.getLastName());
        employeeDao.openCurrentSessionWithTransaction();
        Employee employee = employeeDao.getEntity(employeeDto.getId());
        employeeDto.setVersion(employee.getVersion());
        employeeDao.update(employeeMapper.toEntity(employeeDto));
        employeeDao.closeCurrentSessionWithTransaction();
    }

    public EmployeeDto findById(Long id) {
        log.info("request with below id for find has received : " + id);
        employeeDao.openCurrentSessionWithTransaction();
        Employee employee = employeeDao.getEntity(id);
        employeeDao.closeCurrentSessionWithTransaction();
        return employeeMapper.toDto(employee);
    }

    public void delete(Long id) {
        log.info("request with below id for delete has received : " + id);
        employeeDao.openCurrentSessionWithTransaction();
        Employee employee = employeeDao.getEntity(id);
        employee.setActive(false);
        employee.setDisabled(true);
        employeeDao.closeCurrentSessionWithTransaction();
    }

    public List<EmployeeDto> findAll() {
        log.info("request for find all has received");
        employeeDao.openCurrentSessionWithTransaction();
        List<Employee> employeeList = employeeDao.selectAll();
        employeeDao.closeCurrentSessionWithTransaction();
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        for (Employee employee : employeeList) {
            employeeDtoList.add(employeeMapper.toDto(employee));
        }
        return employeeDtoList;
    }
}

