package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.AttachmentDao;
import com.dotin.timeOffRequest.entity.Attachment;
import org.apache.log4j.Logger;

import java.util.List;

public class AttachmentService {
    private final static Logger log = Logger.getLogger(AttachmentService.class.getName());

    private AttachmentDao attachmentDao;

    public AttachmentService() {
        attachmentDao = new AttachmentDao();
    }

    public Attachment add(Attachment attachment) {
        log.info("object with below info for save has received : " + attachment.getFileName());
        attachmentDao.openCurrentSessionWithTransaction();
        Attachment savedAttachment = attachmentDao.insert(attachment);
        attachmentDao.closeCurrentSessionWithTransaction();
        return savedAttachment;
    }

    public void update(Attachment newAttachment) {
        log.info("object with below info for update has received : " + newAttachment.getFileName());
        attachmentDao.openCurrentSessionWithTransaction();
        Attachment attachment = attachmentDao.getEntity(newAttachment.getId());
        newAttachment.setVersion(attachment.getVersion());
        attachmentDao.update(newAttachment);
        attachmentDao.closeCurrentSessionWithTransaction();
    }

    public Attachment findById(Long id) {
        log.info("request with below id for find has received : " + id);
        attachmentDao.openCurrentSessionWithTransaction();
        Attachment attachment = attachmentDao.getEntity(id);
        attachmentDao.closeCurrentSessionWithTransaction();
        return attachment;
    }

    public void delete(Long id) {
        log.info("request with below id for delete has received : " + id);
        attachmentDao.openCurrentSessionWithTransaction();
        Attachment attachment = attachmentDao.getEntity(id);
        attachment.setActive(false);
        attachment.setDisabled(true);
        attachmentDao.closeCurrentSessionWithTransaction();
    }

    public List<Attachment> findAll() {
        log.info("request for find all has received");
        attachmentDao.openCurrentSessionWithTransaction();
        List<Attachment> attachmentList = attachmentDao.selectAll();
        attachmentDao.closeCurrentSessionWithTransaction();
        return attachmentList;
    }
}

