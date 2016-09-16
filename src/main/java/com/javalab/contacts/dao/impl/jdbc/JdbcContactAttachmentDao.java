package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.model.ContactAttachment;


import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcContactAttachmentDao implements ContactAttachmentDao {

    private ConnectionManager connectionManager = ConnectionManager.getInstance();




    @Override
    public ContactAttachment get(Integer id) {
        PreparedStatement statementGetAttachment = null;
        Connection connection = connectionManager.receiveConnection();
        ContactAttachment resultObject = new ContactAttachment();
        try {
            connection.setAutoCommit(false);
            statementGetAttachment = connection.prepareStatement("SELECT * FROM contact_attachment WHERE id= ?");
            statementGetAttachment.setInt(1, id);
            ResultSet resultSet = statementGetAttachment.executeQuery();
            while (resultSet.next()) {
                resultObject = createAttachmentFromResultSet(resultSet);
            }
            resultSet.close();
            connection.commit();
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
        Connection connection = connectionManager.receiveConnection();
        Collection<ContactAttachment> resultCollection = new ArrayList<>();
        PreparedStatement statementGetByContactId = null;
        try {
            connection.setAutoCommit(false);
            statementGetByContactId = connection.prepareStatement("SELECT * FROM contact_attachment WHERE contact_id=?");
            statementGetByContactId.setInt(1, contactId);
            ResultSet resultSet = statementGetByContactId.executeQuery();
            while (resultSet.next()) {
                resultCollection.add(createAttachmentFromResultSet(resultSet));
            }
            resultSet.close();
            connection.commit();
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
        PreparedStatement statementSaveAttachment = null;
        Connection connection = connectionManager.receiveConnection();
        String queryAddAttachment = "";
        if (contactAttachment.getId() == null) {
            queryAddAttachment = "INSERT INTO contact_attachment " +
                            "(attachment_link, attachment_comment, date_of_upload, contact_id) " +
                            "VALUES (?,?,?,?)";
        } else {
            queryAddAttachment = "UPDATE contact_attachment SET " +
                    "attachment_link=?, attachment_comment=?, date_of_upload=?, contact_id=? " +
                    "WHERE id=" + contactAttachment.getId();
        }
        String attachmentLink = contactAttachment.getAttachmentLink();
        String attachmentComment = contactAttachment.getAttachmentComment();
        String dateOfUpload = contactAttachment.getDateOfUpload().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        try {
            connection.setAutoCommit(false);
            statementSaveAttachment = connection.prepareStatement(queryAddAttachment);
            statementSaveAttachment.setString(1,attachmentLink);
            statementSaveAttachment.setString(2,attachmentComment);
            statementSaveAttachment.setString(3,dateOfUpload);
            statementSaveAttachment.setInt(4,contactId);
            statementSaveAttachment.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statementSaveAttachment != null) {
                    statementSaveAttachment.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionManager.putBackConnection(connection);
        }
    }

    @Override
    public void delete(Integer id) {
        PreparedStatement statementDeleteAttachment = null;
        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            statementDeleteAttachment = connection.prepareStatement("DELETE FROM contact_attachment WHERE id= ?");
            statementDeleteAttachment.setInt(1,id);
            statementDeleteAttachment.executeUpdate();
            connection.commit();
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
        ContactAttachment resultObject = new ContactAttachment();
        resultObject.setId(resultSet.getInt("id"));
        resultObject.setAttachmentLink(resultSet.getString("attachment_link"));
        resultObject.setAttachmentComment(resultSet.getString("attachment_comment"));
        resultObject.setDateOfUpload(resultSet.getDate("date_of_upload").toLocalDate());
        return resultObject;
    }
}
