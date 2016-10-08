package com.javalab.contacts.repository.impl;

import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactAttachmentDao;
import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.exception.ConnectionDeniedException;
import com.javalab.contacts.model.ContactAttachment;
import com.javalab.contacts.repository.AttachmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.javalab.contacts.dao.impl.jdbc.ConnectionManager.closeConnection;
import static com.javalab.contacts.dao.impl.jdbc.ConnectionManager.receiveConnection;


public class AttachmentRepositoryImpl implements AttachmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentRepositoryImpl.class);
    private ContactAttachmentDao attachmentDao = new JdbcContactAttachmentDao();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void delete(Integer id) throws ConnectionDeniedException {
        Connection connection = receiveConnection();
        attachmentDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            attachmentDao.delete(id);
            connection.commit();
        } catch (SQLException e) {
            logger.error("",e);
        } finally {
            closeConnection(connection);
        }

    }

    @Override
    public AttachmentDTO get(Integer id) throws ConnectionDeniedException {
        AttachmentDTO attachmentDTO = null;
        Connection connection = receiveConnection();
        attachmentDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            ContactAttachment attachment = attachmentDao.get(id);
            attachmentDTO = createDtoFromContactAttachment(attachment);
            connection.commit();
        } catch (SQLException e) {
            logger.error("",e);
        } finally {
            closeConnection(connection);
        }
        return attachmentDTO;
    }

    private AttachmentDTO createDtoFromContactAttachment(ContactAttachment attachment){
        Integer id = attachment.getId();
        String attachmentLink = attachment.getAttachmentLink();
        String fileName = attachment.getAttachmentName();
        LocalDate dateOfUpload = attachment.getDateOfUpload();
        String uploadDate = null;
        if (dateOfUpload != null) {
            uploadDate = dateOfUpload.format(formatter);
        }
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
