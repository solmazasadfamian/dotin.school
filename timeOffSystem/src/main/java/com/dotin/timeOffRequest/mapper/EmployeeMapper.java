package com.dotin.timeOffRequest.mapper;

import com.dotin.timeOffRequest.dao.CategoryElementDao;
import com.dotin.timeOffRequest.dao.EmployeeDao;
import com.dotin.timeOffRequest.dto.EmployeeDto;
import com.dotin.timeOffRequest.entity.CategoryElement;
import com.dotin.timeOffRequest.entity.Employee;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EmployeeMapper {
    private final CategoryElementDao categoryElementDao;
    private final EmployeeDao employeeDao;

    public EmployeeMapper() {
        this.categoryElementDao = new CategoryElementDao();
        this.employeeDao = new EmployeeDao();
    }

    public Employee toEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setTimeOffBalance(employeeDto.getTimeOffBalance());
        employee.setVersion(employeeDto.getVersion());
        employee.setLastName(employeeDto.getLastName());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setDisabled(employeeDto.getDisabled());
        employee.setActive(employeeDto.getActive());
        if (employeeDto.getRoleId() != null) {
            Session session = categoryElementDao.openCurrentSession();
            try {
                Transaction transaction = session.beginTransaction();
                CategoryElement role = categoryElementDao.getEntity(employeeDto.getRoleId());
                employee.setRole(role);
                transaction.commit();
            } finally {
                session.close();
            }
        }
        if (employeeDto.getManagerId() != null) {
            Session session = employeeDao.openCurrentSession();
            try {
                Transaction transaction = session.beginTransaction();
                Employee manager = employeeDao.getEntity(employeeDto.getManagerId());
                employee.setManager(manager);
                transaction.commit();
            } finally {
                session.close();
            }
        }
        employee.setEmailAddress(employeeDto.getEmailAddress());
        employee.setAddress(employeeDto.getAddress());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setNationalCode(employeeDto.getNationalCode());
        return employee;
    }

    public EmployeeDto toDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setActive(employee.getActive());
        employeeDto.setAddress(employee.getAddress());
        employeeDto.setDisabled(employee.getDisabled());
        employeeDto.setEmailAddress(employee.getEmailAddress());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setManagerId(employee.getManager() != null ? employee.getManager().getId() : null);
        employeeDto.setNationalCode(employee.getNationalCode());
        employeeDto.setPhoneNumber(employee.getPhoneNumber());
        employeeDto.setRoleId(employee.getRole() != null ? employee.getRole().getId() : null);
        employeeDto.setTimeOffBalance(employee.getTimeOffBalance());
        employeeDto.setVersion(employee.getVersion());
        return employeeDto;
    }
}
