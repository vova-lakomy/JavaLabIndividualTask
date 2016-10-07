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

import static com.javalab.contacts.dao.impl.jdbc.ConnectionManager.closeStatement;

public class JdbcContactAttachmentDao implements ContactAttachmentDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcContactAttachmentDao.class);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Connection connection;

    @Override
    public ContactAttachment get(Integer id) {
        logger.debug("try to get attachment by id= {}", id);
        PreparedStatement statementGetAttachment = null;
        ContactAttachment resultObject = new ContactAttachment();
        try {
            statementGetAttachment = connection.prepareStatement("SELECT * FROM contact_attachment WHERE id= ?");
            statementGetAttachment.setInt(1, id);
            ResultSet resultSet = statementGetAttachment.executeQuery();
            while (resultSet.next()) {
                resultObject = createAttachmentFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            logger.error("{}", e);
        } finally {
            closeStatement(statementGetAttachment);
        }
        if (resultObject.getId() != null) {
            logger.debug("returning {}", resultObject);
            return resultObject;
        } else {
            return null;
        }
    }

    @Override
    public Collection<ContactAttachment> getByContactId(Integer contactId) {
        logger.debug("try to get contacts by contactId= {}", contactId);
        Collection<ContactAttachment> resultCollection = new ArrayList<>();
        PreparedStatement statementGetByContactId = null;
        try {
            statementGetByContactId = connection.prepareStatement("SELECT * FROM contact_attachment WHERE contact_id=?");
            statementGetByContactId.setInt(1, contactId);
            ResultSet resultSet = statementGetByContactId.executeQuery();
            while (resultSet.next()) {
                resultCollection.add(createAttachmentFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            logger.error("{}", e);
        } finally {
            closeStatement(statementGetByContactId);
        }
        logger.debug("returning {}", resultCollection);
        return resultCollection;
    }

    @Override
    public void save(ContactAttachment contactAttachment, Integer contactId) {
        try {
            save(contactAttachment, contactId, connection);
        } catch (SQLException e) {
            logger.error("{}", e);
        }
    }

    @Override
    public void save(ContactAttachment contactAttachment, Integer contactId, Connection connection) throws SQLException {
        logger.debug("saving attachment with id= {} contactId= {}", contactAttachment.getId(), contactId);
        PreparedStatement statementSaveAttachment = null;
        String queryAddAttachment = defineSaveAttachmentQuery(contactAttachment.getId());
        try {
            statementSaveAttachment = connection.prepareStatement(queryAddAttachment);
            setSaveStatementParams(statementSaveAttachment, contactAttachment, contactId);
            statementSaveAttachment.executeUpdate();
        } finally {
          closeStatement(statementSaveAttachment);
        }
    }

    @Override
    public void delete(Integer id) {
        logger.debug("deleting attachment with id= {} ", id);
        PreparedStatement statementDeleteAttachment = null;
        try {
            statementDeleteAttachment = connection.prepareStatement("DELETE FROM contact_attachment WHERE id= ?");
            statementDeleteAttachment.setInt(1, id);
            statementDeleteAttachment.executeUpdate();
        } catch (SQLException e) {
            logger.error("{}", e);
        }
        finally {
            closeStatement(statementDeleteAttachment);
        }
    }

    private ContactAttachment createAttachmentFromResultSet(ResultSet resultSet) throws SQLException {
        logger.debug("creating 'ContactAttachment' entity from {}", resultSet);
        ContactAttachment resultObject = new ContactAttachment();
        Integer id = resultSet.getInt("id");
        String attachmentName = resultSet.getString("attachment_name");
        String attachmentLink = resultSet.getString("attachment_link");
        String attachmentComment = resultSet.getString("attachment_comment");
        Date sqlDateOfUpload = resultSet.getDate("date_of_upload");
        resultObject.setId(id);
        resultObject.setAttachmentName(attachmentName);
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
                    + "(attachment_name, attachment_link, attachment_comment, date_of_upload, contact_id) "
                    + "VALUES (?, ?, ?, ?, ?)";
        } else {
            return "UPDATE contact_attachment SET "
                    + "attachment_name=?, attachment_link=?, attachment_comment=?, date_of_upload=?, contact_id=? "
                    + "WHERE id=" + attachmentId;
        }
    }

    private void setSaveStatementParams(PreparedStatement statement, ContactAttachment attachment, Integer contactId) throws SQLException {
        logger.debug("setting params to save attachment statement");
        statement.setString(1, attachment.getAttachmentName());
        statement.setString(2, attachment.getAttachmentLink());
        statement.setString(3, attachment.getAttachmentComment());
        String stringDate = null;
        if (attachment.getDateOfUpload() != null) {
            stringDate = attachment.getDateOfUpload().format(formatter);
        }
        statement.setString(4, stringDate);
        statement.setInt(5, contactId);
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
