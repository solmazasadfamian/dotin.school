package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.EmailDao;
import com.dotin.timeOffRequest.dto.EmailDto;
import com.dotin.timeOffRequest.entity.Email;
import com.dotin.timeOffRequest.mapper.EmailMapper;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        Session session = emailDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Email email = emailMapper.toEntity(emailDto);
            Email savedEmail = emailDao.insert(email);
            transaction.commit();
            return emailMapper.toDto(savedEmail);
        } finally {
            session.close();
        }
    }

    public void update(EmailDto newEmail) {
        log.info("object with below info for update has received : " + newEmail.getSubject());
        Session session = emailDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Email email = emailDao.getEntity(newEmail.getId());
            newEmail.setVersion(email.getVersion());
            emailDao.update(emailMapper.toEntity(newEmail));
            transaction.commit();
        } finally {
            session.close();
        }
    }

    public EmailDto findById(Long id) {
        log.info("request with below id for find has received : " + id);
        Session session = emailDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Email email = emailDao.getEntity(id);
            transaction.commit();
            return emailMapper.toDto(email);
        } finally {
            session.close();
        }
    }

    public void delete(Long id) {
        log.info("request with below id for delete has received : " + id);
        Session session = emailDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Email email = emailDao.getEntity(id);
            email.setActive(false);
            email.setDisabled(true);
            transaction.commit();
        } finally {
            session.close();
        }
    }

    public List<EmailDto> findAll() {
        log.info("request for find all has received");
        Session session = emailDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            List<Email> emailList = emailDao.selectAll();
            List<EmailDto> emailDtoList = new ArrayList<>();
            for (Email email : emailList) {
                emailDtoList.add(emailMapper.toDto(email));
            }
            transaction.commit();
            return emailDtoList;
        } finally {
            session.close();
        }
    }

    public List<Email> findAllByReceiverId(Long id) {
        Session session = emailDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            List<Email> emailList = emailDao.findAllByReceiverId(id);
            transaction.commit();
            return emailList;
        } finally {
            session.close();
        }
    }

    public List<Email> findAllBySenderId(Long id) {
        Session session = emailDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            List<Email> emailList = emailDao.findAllBySenderId(id);
            transaction.commit();
            return emailList;
        } finally {
            session.close();
        }

    }

}

