package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.model.ContactAttachment;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;

import static com.javalab.contacts.dao.impl.jdbc.DbConnectionProvider.putBackConnection;
import static com.javalab.contacts.dao.impl.jdbc.DbConnectionProvider.receiveConnection;
import static com.javalab.contacts.dao.impl.jdbc.StatementExecutor.executeStatement;


public class JdbcContactAttachmentDao implements ContactAttachmentDao {

    public ContactAttachment get(Integer id) {
        Connection connection = receiveConnection();
        ContactAttachment resultObject = new ContactAttachment();
        String query = "SELECT * FROM contact_attachment WHERE id=" + id;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                resultObject.setId(resultSet.getInt("id"));
                resultObject.setAttachmentLink(resultSet.getString("attachment_link"));
                resultObject.setAttachmentComment(resultSet.getString("attachment_comment"));
                resultObject.setDateOfUpload(resultSet.getDate("date_of_upload").toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        putBackConnection(connection);
        return resultObject.getId() != null ? resultObject : null;
    }

    public Collection<ContactAttachment> getByContactId(Integer contactId) {
        Connection connection = receiveConnection();
        Collection<ContactAttachment> resultCollection = new HashSet<>();
        String query = "SELECT * FROM contact_attachment WHERE contact_id=" + contactId;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ContactAttachment attachment = new ContactAttachment();
                attachment.setId(resultSet.getInt("id"));
                attachment.setAttachmentLink(resultSet.getString("attachment_link"));
                attachment.setAttachmentComment(resultSet.getString("attachment_comment"));
                attachment.setDateOfUpload(resultSet.getDate("date_of_upload").toLocalDate());
                resultCollection.add(attachment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        putBackConnection(connection);
        return resultCollection;
    }

    public void save(ContactAttachment contactAttachment, Integer contactId) {
        String attachmentLink = contactAttachment.getAttachmentLink();
        String attachmentComment = contactAttachment.getAttachmentComment();
        String dateOfUpload = contactAttachment.getDateOfUpload().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String query;

        if (contactAttachment.getId() == null) {
            query = "INSERT INTO contact_attachment " +
                    "(attachment_link, " +
                    "attachment_comment, " +
                    "date_of_upload, " +
                    "contact_id) " +
                    "VALUES ('" +
                    attachmentLink + "','" +
                    attachmentComment + "','" +
                    dateOfUpload + "'," +
                    contactId + ")";
        } else {
            query = "UPDATE contact_attachment SET " +
                    "attachment_link='" + attachmentLink +
                    "', attachment_comment='" + attachmentComment +
                    "', date_of_upload='" + dateOfUpload +
                    "', contact_id=" + contactId +
                    " WHERE id=" + contactAttachment.getId();
        }
        executeStatement(query);
    }

    public void delete(int id) {
        executeStatement("DELETE FROM contact_attachment WHERE id=" + id);
    }
}
