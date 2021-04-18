package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.EmployeeDao;
import com.dotin.timeOffRequest.dto.EmployeeDto;
import com.dotin.timeOffRequest.entity.Employee;
import com.dotin.timeOffRequest.exception.BadRequestException;
import com.dotin.timeOffRequest.exception.ErrorMessages;
import com.dotin.timeOffRequest.mapper.EmployeeMapper;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

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
        Session session = employeeDao.openCurrentSession();
        try {
            employeeDto.setTimeOffBalance(240);
            Transaction transaction = session.beginTransaction();
            Employee employee = employeeMapper.toEntity(employeeDto);
            Employee saveEmployee = employeeDao.insert(employee);
            transaction.commit();
            return employeeMapper.toDto(saveEmployee);
        } finally {
            session.close();
        }
    }

    public void update(EmployeeDto employeeDto) {
        log.info("object with below info for update has received : " + employeeDto.getFirstName() + " " + employeeDto.getLastName());
        Session session = employeeDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Employee employee = employeeDao.getEntity(employeeDto.getId());
            employeeDto.setVersion(employee.getVersion());
            employeeDao.update(employeeMapper.toEntity(employeeDto));
            transaction.commit();
        } finally {
            session.close();
        }
    }

    public EmployeeDto findById(Long id) {
        log.info("request with below id for find has received : " + id);
        Session session = employeeDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Employee employee = employeeDao.getEntity(id);
            transaction.commit();
            return employeeMapper.toDto(employee);
        } finally {
            session.close();
        }
    }

    public void delete(Long id) throws BadRequestException {
        log.info("request with below id for delete has received : " + id);
        List<EmployeeDto> employeeDtoList = findAllById("manager.id", id);
        Session session = employeeDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            if (employeeDtoList.size() == 0) {
                Employee employee = employeeMapper.toEntity(findById(id));
                employee.setActive(false);
                update(employeeMapper.toDto(employee));
            } else {
                throw new BadRequestException(ErrorMessages.MANAGER_DELETE_CODE, ErrorMessages.MANAGER_DELETE_MESSAGE);
            }
            transaction.commit();
        } finally {
            session.close();
        }
    }

    public List<EmployeeDto> findAll() {
        log.info("request for find all has received");
        Session session = employeeDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            List<Employee> employeeList = employeeDao.selectAll();
            List<EmployeeDto> employeeDtoList = new ArrayList<>();
            for (Employee employee : employeeList) {
                employeeDtoList.add(employeeMapper.toDto(employee));
            }
            transaction.commit();
            return employeeDtoList;
        } finally {
            session.close();
        }
    }

    public List<EmployeeDto> findAllById(String string, Long id) {
        log.info("request for find all with below id has received : " + id);
        Session session = employeeDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(Employee.class);
            criteria.add(Restrictions.eq("active", true)).add(Restrictions.eq(string, id));
            List<Employee> employeeList = criteria.list();
            List<EmployeeDto> employeeDtoList = new ArrayList<>();
            for (Employee employee : employeeList) {
                employeeDtoList.add(employeeMapper.toDto(employee));
            }
            transaction.commit();
            return employeeDtoList;
        } finally {
            session.close();
        }
    }
}

