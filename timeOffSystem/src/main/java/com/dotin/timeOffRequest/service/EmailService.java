package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.EmailDao;
import com.dotin.timeOffRequest.entity.Email;
import org.apache.log4j.Logger;

import java.util.List;

public class EmailService {
    private final static Logger log = Logger.getLogger(EmailService.class.getName());

    private EmailDao emailDao;

    public EmailService() {
        emailDao = new EmailDao();
    }

    public Email add(Email email) {
        log.info("object with below info for save has received : " + email.getSubject());
        emailDao.openCurrentSessionWithTransaction();
        Email savedEmail = emailDao.insert(email);
        emailDao.closeCurrentSessionWithTransaction();
        return savedEmail;
    }

    public void update(Email newEmail) {
        log.info("object with below info for update has received : " + newEmail.getSubject());
        emailDao.openCurrentSessionWithTransaction();
        Email email = emailDao.getEntity(newEmail.getId());
        newEmail.setVersion(email.getVersion());
        emailDao.update(newEmail);
        emailDao.closeCurrentSessionWithTransaction();
    }

    public Email findById(Long id) {
        log.info("request with below id for find has received : " + id);
        emailDao.openCurrentSessionWithTransaction();
        Email email = emailDao.getEntity(id);
        emailDao.closeCurrentSessionWithTransaction();
        return email;
    }

    public void delete(Long id) {
        log.info("request with below id for delete has received : " + id);
        emailDao.openCurrentSessionWithTransaction();
        Email email = emailDao.getEntity(id);
        email.setActive(false);
        email.setDisabled(true);
        emailDao.closeCurrentSessionWithTransaction();
    }

    public List<Email> findAll() {
        log.info("request for find all has received");
        emailDao.openCurrentSessionWithTransaction();
        List<Email> emailList = emailDao.selectAll();
        emailDao.closeCurrentSessionWithTransaction();
        return emailList;
    }
}

