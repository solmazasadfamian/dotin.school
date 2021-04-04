package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.EmailDao;
import com.dotin.timeOffRequest.dto.EmailDto;
import com.dotin.timeOffRequest.entity.Email;
import com.dotin.timeOffRequest.mapper.EmailMapper;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class EmailService {
    private final static Logger log = Logger.getLogger(EmailService.class.getName());

    private final EmailDao emailDao;
    private final EmailMapper emailMapper;

    public EmailService() {
        emailDao = new EmailDao();
        emailMapper = new EmailMapper();
    }

    public EmailDto add(EmailDto emailDto) {
        log.info("object with below info for save has received : " + emailDto.toString());
        Email email = emailMapper.toEntity(emailDto);
        emailDao.openCurrentSessionWithTransaction();
        Email savedEmail = emailDao.insert(email);
        emailDao.closeCurrentSessionWithTransaction();
        return emailMapper.toDto(savedEmail);
    }

    public void update(EmailDto newEmail) {
        log.info("object with below info for update has received : " + newEmail.getSubject());
        emailDao.openCurrentSessionWithTransaction();
        Email email = emailDao.getEntity(newEmail.getId());
        newEmail.setVersion(email.getVersion());
        emailDao.update(emailMapper.toEntity(newEmail));
        emailDao.closeCurrentSessionWithTransaction();
    }

    public EmailDto findById(Long id) {
        log.info("request with below id for find has received : " + id);
        emailDao.openCurrentSessionWithTransaction();
        Email email = emailDao.getEntity(id);
        emailDao.closeCurrentSessionWithTransaction();
        return emailMapper.toDto(email);
    }

    public void delete(Long id) {
        log.info("request with below id for delete has received : " + id);
        emailDao.openCurrentSessionWithTransaction();
        Email email = emailDao.getEntity(id);
        email.setActive(false);
        email.setDisabled(true);
        emailDao.closeCurrentSessionWithTransaction();
    }

    public List<EmailDto> findAll() {
        log.info("request for find all has received");
        emailDao.openCurrentSessionWithTransaction();
        List<Email> emailList = emailDao.selectAll();
        emailDao.closeCurrentSessionWithTransaction();
        List<EmailDto> emailDtoList = new ArrayList<>();
        for (Email email : emailList) {
            emailDtoList.add(emailMapper.toDto(email));
        }
        return emailDtoList;
    }
}

