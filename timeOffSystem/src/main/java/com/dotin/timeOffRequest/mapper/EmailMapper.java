package com.dotin.timeOffRequest.mapper;

import com.dotin.timeOffRequest.dao.EmployeeDao;
import com.dotin.timeOffRequest.dto.EmailDto;
import com.dotin.timeOffRequest.entity.Email;
import com.dotin.timeOffRequest.entity.Employee;

import java.util.HashSet;
import java.util.Set;

public class EmailMapper {
    private final EmployeeDao employeeDao;

    public EmailMapper() {
        this.employeeDao = new EmployeeDao();
    }

    public Email toEntity(EmailDto emailDto) {
        Email email = new Email();
        email.setId(emailDto.getId());
        email.setSubject(emailDto.getSubject());
        email.setDescription(emailDto.getDescription());
        email.setActive(emailDto.getActive());
        email.setDisabled(emailDto.getDisabled());
        if (emailDto.getSenderId() != null) {
            employeeDao.openCurrentSessionWithTransaction();
            Employee sender = employeeDao.getEntity(emailDto.getSenderId());
            employeeDao.closeCurrentSessionWithTransaction();
            email.setSender(sender);
        }
        Set<Employee> receiverList = new HashSet<>();
        for (Long id : emailDto.getReceiverId()) {
            employeeDao.openCurrentSessionWithTransaction();
            Employee receiver = employeeDao.getEntity(id);
            receiverList.add(receiver);
            employeeDao.closeCurrentSessionWithTransaction();
        }
        email.setReceiver(receiverList);
        return email;
    }

    public EmailDto toDto(Email email) {
        EmailDto emailDto = new EmailDto();
        emailDto.setId(email.getId());
        emailDto.setActive(email.getActive());
        emailDto.setDescription(email.getDescription());
        emailDto.setDisabled(email.getDisabled());
        emailDto.setSubject(email.getSubject());
        emailDto.setVersion(email.getVersion());
        emailDto.setSenderId(email.getSender() != null ? email.getSender().getId() : null);
        Set<Long> receiverList = new HashSet<>();
        for (Employee employee : email.getReceiver()) {
            receiverList.add(employee.getId());
        }
        emailDto.setReceiverId(receiverList);
        return emailDto;
    }
}
