package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.model.ContactAttachment;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import static com.javalab.contacts.dao.impl.jdbc.DbConnectionProvider.putBackConnection;
import static com.javalab.contacts.dao.impl.jdbc.DbConnectionProvider.receiveConnection;
import static com.javalab.contacts.dao.impl.jdbc.StatementExecutor.executeStatement;


public class JdbcContactAttachmentDao implements ContactAttachmentDao {

    @Override
    public ContactAttachment get(Integer id) {
        Connection connection = receiveConnection();
        ContactAttachment resultObject = new ContactAttachment();
        String query = "SELECT * FROM contact_attachment WHERE id=" + id;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                resultObject = createAttachmentFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        putBackConnection(connection);
        return resultObject.getId() != null ? resultObject : null;
    }

    @Override
    public Collection<ContactAttachment> getByContactId(Integer contactId) {
        Connection connection = receiveConnection();
        Collection<ContactAttachment> resultCollection = new ArrayList<>();
        String query = "SELECT * FROM contact_attachment WHERE contact_id=" + contactId;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                resultCollection.add(createAttachmentFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        putBackConnection(connection);
        return resultCollection;
    }

    @Override
    public void save(ContactAttachment contactAttachment, Integer contactId) {
        if (contactAttachment != null) {

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
    }

    @Override
    public void delete(int id) {
        executeStatement("DELETE FROM contact_attachment WHERE id=" + id);
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
