package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.AttachmentDao;
import com.dotin.timeOffRequest.dto.AttachmentDto;
import com.dotin.timeOffRequest.entity.Attachment;
import com.dotin.timeOffRequest.mapper.AttachmentMapper;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class AttachmentService {
    private final static Logger log = Logger.getLogger(AttachmentService.class.getName());

    private final AttachmentDao attachmentDao;
    private final AttachmentMapper attachmentMapper;

    public AttachmentService() {
        attachmentDao = new AttachmentDao();
        attachmentMapper = new AttachmentMapper();
    }

    public AttachmentDto add(AttachmentDto attachmentDto) {
        log.info("object with below info for save has received : " + attachmentDto.getFileName());
        Session session = attachmentDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Attachment attachment = attachmentMapper.toEntity(attachmentDto);
            Attachment savedAttachment = attachmentDao.insert(attachment);
            transaction.commit();
            return attachmentMapper.toDto(savedAttachment);
        } finally {
            session.close();
        }
    }

    public void update(AttachmentDto newAttachmentDto) {
        log.info("object with below info for update has received : " + newAttachmentDto.getFileName());
        Session session = attachmentDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Attachment attachment = attachmentDao.getEntity(newAttachmentDto.getId());
            newAttachmentDto.setVersion(attachment.getVersion());
            attachmentDao.update(attachmentMapper.toEntity(newAttachmentDto));
            transaction.commit();
        } finally {
            session.close();
        }
    }

    public AttachmentDto findById(Long id) {
        log.info("request with below id for find has received : " + id);
        Session session = attachmentDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Attachment attachment = attachmentDao.getEntity(id);
            transaction.commit();
            return attachmentMapper.toDto(attachment);
        } finally {
            session.close();
        }
    }

    public void delete(Long id) {
        log.info("request with below id for delete has received : " + id);
        Session session = attachmentDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            Attachment attachment = attachmentDao.getEntity(id);
            attachment.setActive(false);
            attachment.setDisabled(true);
            transaction.commit();
        } finally {
            session.close();
        }
    }

    public List<AttachmentDto> findAll() {
        log.info("request for find all has received");
        Session session = attachmentDao.openCurrentSession();
        try {
            Transaction transaction = session.beginTransaction();
            List<Attachment> attachmentList = attachmentDao.selectAll();
            List<AttachmentDto> attachmentDtoList = new ArrayList<>();
            for (Attachment attachment : attachmentList) {
                attachmentDtoList.add(attachmentMapper.toDto(attachment));
            }
            transaction.commit();
            return attachmentDtoList;
        } finally {
            session.close();
        }
    }
}

