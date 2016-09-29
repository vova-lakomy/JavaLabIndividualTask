package com.javalab.contacts.repository.impl;

import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactAttachmentDao;
import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.model.ContactAttachment;
import com.javalab.contacts.repository.AttachmentDtoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;


public class AttachmentDtoRepositoryImpl implements AttachmentDtoRepository {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentDtoRepositoryImpl.class);
    private ContactAttachmentDao attachmentDao = new JdbcContactAttachmentDao();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void delete(Integer id) {
        attachmentDao.delete(id);
    }

    @Override
    public Collection<AttachmentDTO> getByContactId(Integer contactId) {
        if (contactId != null) {
            Collection<AttachmentDTO> attachmentDTOs = new ArrayList<>();
            Collection<ContactAttachment> attachments = attachmentDao.getByContactId(contactId);
            attachments.forEach(attachment -> {
                AttachmentDTO attachmentDTO = createDtoFromContactAttachment(attachment);
                attachmentDTOs.add(attachmentDTO);
            });
            if (attachmentDTOs.size() > 0) {
                return attachmentDTOs;
            } else return null;
        } else return null;
    }

    @Override
    public AttachmentDTO get(Integer id){
        ContactAttachment attachment = attachmentDao.get(id);
        return createDtoFromContactAttachment(attachment);
    }

    private AttachmentDTO createDtoFromContactAttachment(ContactAttachment attachment){
        Integer id = attachment.getId();
        String attachmentLink = attachment.getAttachmentLink();
        String fileName = attachmentLink.substring(attachmentLink.lastIndexOf("/") + 1);
        String uploadDate = attachment.getDateOfUpload().format(formatter);
        String comment = attachment.getAttachmentComment();
        AttachmentDTO attachmentDTO = new AttachmentDTO();
        attachmentDTO.setId(id);
        attachmentDTO.setAttachmentLink(attachmentLink);
        attachmentDTO.setFileName(fileName);
        attachmentDTO.setUploadDate(uploadDate);
        attachmentDTO.setComment(comment);
        return attachmentDTO;
    }
}
