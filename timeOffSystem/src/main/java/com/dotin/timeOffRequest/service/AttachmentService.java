package com.dotin.timeOffRequest.service;

import com.dotin.timeOffRequest.dao.AttachmentDao;
import com.dotin.timeOffRequest.dto.AttachmentDto;
import com.dotin.timeOffRequest.entity.Attachment;
import com.dotin.timeOffRequest.mapper.AttachmentMapper;
import org.apache.log4j.Logger;

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
        Attachment attachment = attachmentMapper.toEntity(attachmentDto);
        attachmentDao.openCurrentSessionWithTransaction();
        Attachment savedAttachment = attachmentDao.insert(attachment);
        attachmentDao.closeCurrentSessionWithTransaction();
        return attachmentMapper.toDto(savedAttachment);
    }

    public void update(AttachmentDto newAttachmentDto) {
        log.info("object with below info for update has received : " + newAttachmentDto.getFileName());
        attachmentDao.openCurrentSessionWithTransaction();
        Attachment attachment = attachmentDao.getEntity(newAttachmentDto.getId());
        newAttachmentDto.setVersion(attachment.getVersion());
        attachmentDao.update(attachmentMapper.toEntity(newAttachmentDto));
        attachmentDao.closeCurrentSessionWithTransaction();
    }

    public AttachmentDto findById(Long id) {
        log.info("request with below id for find has received : " + id);
        attachmentDao.openCurrentSessionWithTransaction();
        Attachment attachment = attachmentDao.getEntity(id);
        attachmentDao.closeCurrentSessionWithTransaction();
        return attachmentMapper.toDto(attachment);
    }

    public void delete(Long id) {
        log.info("request with below id for delete has received : " + id);
        attachmentDao.openCurrentSessionWithTransaction();
        Attachment attachment = attachmentDao.getEntity(id);
        attachment.setActive(false);
        attachment.setDisabled(true);
        attachmentDao.closeCurrentSessionWithTransaction();
    }

    public List<AttachmentDto> findAll() {
        log.info("request for find all has received");
        attachmentDao.openCurrentSessionWithTransaction();
        List<Attachment> attachmentList = attachmentDao.selectAll();
        attachmentDao.closeCurrentSessionWithTransaction();
        List<AttachmentDto> attachmentDtoList = new ArrayList<>();
        for (Attachment attachment : attachmentList) {
            attachmentDtoList.add(attachmentMapper.toDto(attachment));
        }
        return attachmentDtoList;
    }
}

