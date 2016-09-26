package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.model.ContactAttachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcContactAttachmentDao implements ContactAttachmentDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcContactAttachmentDao.class);

    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public ContactAttachment get(Integer id) {
        logger.debug("try to get attachment by id=" + id);
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
            e.printStackTrace();
        } finally {
            try {
                if (statementGetAttachment != null) {
                    statementGetAttachment.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionManager.putBackConnection(connection);
        }
        return resultObject.getId() != null ? resultObject : null;
    }

    @Override
    public Collection<ContactAttachment> getByContactId(Integer contactId) {
        logger.debug("try to get contacts by contactId=" + contactId);
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
            e.printStackTrace();
        } finally {
            try {
                if (statementGetByContactId != null) {
                    statementGetByContactId.close();
                }
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
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
            save(contactAttachment,contactId,connection);
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionManager.putBackConnection(connection);
        }
    }

    @Override
    public void save(ContactAttachment contactAttachment, Integer contactId, Connection connection){
        logger.debug("saving attachment with id= " + contactAttachment.getId() + " contactId= " + contactId);
        PreparedStatement statementSaveAttachment = null;
        String queryAddAttachment = defineSaveAttachmentQuery(contactAttachment.getId());
        try {
            statementSaveAttachment = connection.prepareStatement(queryAddAttachment);
            setSaveStatementParams(statementSaveAttachment,contactAttachment,contactId);
            statementSaveAttachment.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statementSaveAttachment != null) {
                    statementSaveAttachment.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Integer id) {
        logger.debug("deleting attachment with id= " + id);
        PreparedStatement statementDeleteAttachment = null;
        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            statementDeleteAttachment = connection.prepareStatement("DELETE FROM contact_attachment WHERE id= ?");
            statementDeleteAttachment.setInt(1,id);
            statementDeleteAttachment.executeUpdate();
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statementDeleteAttachment != null) {
                    statementDeleteAttachment.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionManager.putBackConnection(connection);
        }
    }

    private ContactAttachment createAttachmentFromResultSet(ResultSet resultSet) throws SQLException {
        logger.debug("creating 'ContactAttachment' entity from " + resultSet);
        ContactAttachment resultObject = new ContactAttachment();
        resultObject.setId(resultSet.getInt("id"));
        resultObject.setAttachmentLink(resultSet.getString("attachment_link"));
        resultObject.setAttachmentComment(resultSet.getString("attachment_comment"));
        resultObject.setDateOfUpload(resultSet.getDate("date_of_upload").toLocalDate());
        return resultObject;
    }

    private String defineSaveAttachmentQuery(Integer attachmentId){
        logger.debug("defining save attachment query string");
        if (attachmentId == null){
            return "INSERT INTO contact_attachment " +
                    "(attachment_link, attachment_comment, date_of_upload, contact_id) " +
                    "VALUES (?,?,?,?)";
        } else {
            return "UPDATE contact_attachment SET " +
                    "attachment_link=?, attachment_comment=?, date_of_upload=?, contact_id=? " +
                    "WHERE id=" + attachmentId;
        }
    }

    private void setSaveStatementParams(PreparedStatement statement, ContactAttachment attachment, Integer contactId) throws SQLException {
        logger.debug("setting save attachment statement params");
        statement.setString(1,attachment.getAttachmentLink());
        statement.setString(2,attachment.getAttachmentComment());
        statement.setString(3,attachment.getDateOfUpload().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        statement.setInt(4,contactId);
    }
}
