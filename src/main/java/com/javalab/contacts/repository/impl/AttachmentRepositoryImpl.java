package com.javalab.contacts.repository.impl;

import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.dao.impl.jdbc.ConnectionManager;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactAttachmentDao;
import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.model.ContactAttachment;
import com.javalab.contacts.repository.AttachmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AttachmentRepositoryImpl implements AttachmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentRepositoryImpl.class);
    private ContactAttachmentDao attachmentDao = new JdbcContactAttachmentDao();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void delete(Integer id) throws ConnectionFailedException {
        logger.debug("try to delete attachment info by id={}", id);
        Connection connection = ConnectionManager.receiveConnection();
        attachmentDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            logger.debug("transaction opened");
            attachmentDao.delete(id);
            connection.commit();
            logger.debug("transaction closed ");
        } catch (SQLException e) {
            logger.error("",e);
            try {
                logger.error("error while deleting attachment, start changes rollback");
                connection.rollback();
                logger.error("rollback done");
            } catch (SQLException e1) {
                logger.error("",e);
            }
            throw new ConnectionFailedException();
        } finally {
            ConnectionManager.closeConnection(connection);
        }
    }

    @Override
    public AttachmentDTO get(Integer id) throws ConnectionFailedException {
        logger.debug("trying to get attachment info by id={}", id);
        AttachmentDTO attachmentDTO = null;
        Connection connection = ConnectionManager.receiveConnection();
        attachmentDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            logger.debug("transaction opened");
            ContactAttachment attachment = attachmentDao.get(id);
            connection.commit();
            logger.debug("transaction closed");
            attachmentDTO = createDtoFromContactAttachment(attachment);
        } catch (SQLException e) {
            logger.error("",e);
            throw new ConnectionFailedException();
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        return attachmentDTO;
    }

    private AttachmentDTO createDtoFromContactAttachment(ContactAttachment attachment){
        logger.debug("creating attachment DTO from {}", attachment);
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
        logger.debug("attachment DTO created");
        return attachmentDTO;
    }
}
