package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.model.ContactAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcContactAttachmentDao implements ContactAttachmentDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcContactAttachmentDao.class);
    private ConnectionManager connectionManager = ConnectionManager.getInstance();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public ContactAttachment get(Integer id) {
        logger.debug("try to get attachment by id= {}", id);
        PreparedStatement statementGetAttachment = null;
        Connection connection = connectionManager.receiveConnection();
        ContactAttachment resultObject = new ContactAttachment();
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            statementGetAttachment = connection.prepareStatement("SELECT * FROM contact_attachment WHERE id= ?");
            statementGetAttachment.setInt(1, id);
            ResultSet resultSet = statementGetAttachment.executeQuery();
            while (resultSet.next()) {
                resultObject = createAttachmentFromResultSet(resultSet);
            }
            resultSet.close();
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            logger.error("{}", e);
        } finally {
            try {
                if (statementGetAttachment != null) {
                    statementGetAttachment.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("{}", e);
            }
            connectionManager.putBackConnection(connection);
        }
        if (resultObject.getId() != null) {
            return resultObject;
        } else {
            return null;
        }
    }

    @Override
    public Collection<ContactAttachment> getByContactId(Integer contactId) {
        logger.debug("try to get contacts by contactId= {}", contactId);
        Connection connection = connectionManager.receiveConnection();
        Collection<ContactAttachment> resultCollection = new ArrayList<>();
        PreparedStatement statementGetByContactId = null;
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            statementGetByContactId = connection.prepareStatement("SELECT * FROM contact_attachment WHERE contact_id=?");
            statementGetByContactId.setInt(1, contactId);
            ResultSet resultSet = statementGetByContactId.executeQuery();
            while (resultSet.next()) {
                resultCollection.add(createAttachmentFromResultSet(resultSet));
            }
            resultSet.close();
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            logger.error("{}", e);
        } finally {
            try {
                if (statementGetByContactId != null) {
                    statementGetByContactId.close();
                }
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                logger.error("{}", e);
            }
            connectionManager.putBackConnection(connection);
        }
        return resultCollection;
    }

    @Override
    public void save(ContactAttachment contactAttachment, Integer contactId) {

        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            save(contactAttachment, contactId, connection);
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("{}", e1);
            }
            logger.error("{}", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("{}", e);
            }
            connectionManager.putBackConnection(connection);
        }
    }

    @Override
    public void save(ContactAttachment contactAttachment, Integer contactId, Connection connection){
        logger.debug("saving attachment with id= {} contactId= {}", contactAttachment.getId(), contactId);
        PreparedStatement statementSaveAttachment = null;
        String queryAddAttachment = defineSaveAttachmentQuery(contactAttachment.getId());
        try {
            statementSaveAttachment = connection.prepareStatement(queryAddAttachment);
            setSaveStatementParams(statementSaveAttachment, contactAttachment, contactId);
            statementSaveAttachment.executeUpdate();
        } catch (SQLException e) {
            logger.error("{}", e);
        } finally {
            try {
                if (statementSaveAttachment != null) {
                    statementSaveAttachment.close();
                }
            } catch (SQLException e) {
                logger.error("{}", e);
            }
        }
    }

    @Override
    public void delete(Integer id) {
        logger.debug("deleting attachment with id= {} ", id);
        PreparedStatement statementDeleteAttachment = null;
        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            statementDeleteAttachment = connection.prepareStatement("DELETE FROM contact_attachment WHERE id= ?");
            statementDeleteAttachment.setInt(1, id);
            statementDeleteAttachment.executeUpdate();
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            logger.error("{}", e);
        }
        finally {
            try {
                if (statementDeleteAttachment != null) {
                    statementDeleteAttachment.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("{}", e);
            }
            connectionManager.putBackConnection(connection);
        }
    }

    private ContactAttachment createAttachmentFromResultSet(ResultSet resultSet) throws SQLException {
        logger.debug("creating 'ContactAttachment' entity from {}", resultSet);
        ContactAttachment resultObject = new ContactAttachment();
        Integer id = resultSet.getInt("id");
        String attachmentLink = resultSet.getString("attachment_link");
        String attachmentComment = resultSet.getString("attachment_comment");
        Date sqlDateOfUpload = resultSet.getDate("date_of_upload");
        resultObject.setId(id);
        resultObject.setAttachmentLink(attachmentLink);
        resultObject.setAttachmentComment(attachmentComment);
        LocalDate dateOfUpload = null;
        if (sqlDateOfUpload != null) {
            dateOfUpload = sqlDateOfUpload.toLocalDate();
        }
        resultObject.setDateOfUpload(dateOfUpload);
        logger.debug("created {}", resultObject);
        return resultObject;
    }

    private String defineSaveAttachmentQuery(Integer attachmentId){
        logger.debug("defining save attachment query string");
        if (attachmentId == null){
            return "INSERT INTO contact_attachment "
                    + "(attachment_link, attachment_comment, date_of_upload, contact_id) "
                    + "VALUES (?,?,?,?)";
        } else {
            return "UPDATE contact_attachment SET "
                    + "attachment_link=?, attachment_comment=?, date_of_upload=?, contact_id=? "
                    + "WHERE id=" + attachmentId;
        }
    }

    private void setSaveStatementParams(PreparedStatement statement, ContactAttachment attachment, Integer contactId) throws SQLException {
        logger.debug("setting params to save attachment statement");
        statement.setString(1, attachment.getAttachmentLink());
        statement.setString(2, attachment.getAttachmentComment());
        String stringDate = null;
        if (attachment.getDateOfUpload() != null) {
            stringDate = attachment.getDateOfUpload().format(formatter);
        }
        statement.setString(3, stringDate);
        statement.setInt(4, contactId);
    }
}
