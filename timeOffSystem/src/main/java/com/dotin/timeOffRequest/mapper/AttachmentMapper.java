package com.dotin.timeOffRequest.mapper;

import com.dotin.timeOffRequest.dao.EmailDao;
import com.dotin.timeOffRequest.dto.AttachmentDto;
import com.dotin.timeOffRequest.entity.Attachment;
import com.dotin.timeOffRequest.entity.Email;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AttachmentMapper {
    private final EmailDao emailDao;

    public AttachmentMapper() {
        this.emailDao = new EmailDao();
    }

    public Attachment toEntity(AttachmentDto attachmentDto) {
        Attachment attachment = new Attachment();
        attachment.setId(attachmentDto.getId());
        attachment.setFileName(attachmentDto.getFileName());
        if (attachmentDto.getEmailId() != null) {
            Session session = emailDao.openCurrentSession();
            try {
                Transaction transaction = session.beginTransaction();
                Email email = emailDao.getEntity(attachmentDto.getEmailId());
                transaction.commit();
                attachment.setEmail(email);
            } finally {
                session.close();
            }
        }
        attachment.setDisabled(attachmentDto.getDisabled());
        attachment.setActive(attachmentDto.getActive());
        attachment.setVersion(attachmentDto.getVersion());
        return attachment;
    }

    public AttachmentDto toDto(Attachment attachment) {
        AttachmentDto attachmentDto = new AttachmentDto();
        attachmentDto.setId(attachment.getId());
        attachmentDto.setActive(attachment.getActive());
        attachmentDto.setDisabled(attachment.getDisabled());
        attachmentDto.setEmailId(attachment.getId());
        attachmentDto.setFileName(attachment.getFileName());
        attachmentDto.setVersion(attachment.getVersion());
        return attachmentDto;
    }
}
