package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.ContactAddressDao;
import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.dao.ContactDao;
import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.model.Contact;
import com.javalab.contacts.model.enumerations.MartialStatus;
import com.javalab.contacts.model.enumerations.Sex;

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


public class JdbcContactDao implements ContactDao {

    private ContactAddressDao addressDao = new JdbcContactAddressDao();
    private PhoneNumberDao phoneNumberDao = new JdbcPhoneNumberDao();
    private ContactAttachmentDao attachmentDao = new JdbcContactAttachmentDao();

    @Override
    public Contact get(Integer id) {
        Connection connection = receiveConnection();
        Contact resultObject = new Contact();
        String query = "SELECT * FROM contact WHERE id=" + id;
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                resultObject = createContactFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        putBackConnection(connection);
        return resultObject.getId() != null ? resultObject : null;
    }

    @Override
    public Collection<Contact> getAllContacts(){
        Collection<Contact> resultCollection = new ArrayList<>();
        Connection connection = receiveConnection();

        String query = "SELECT * FROM contact ORDER BY last_name";
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                resultCollection.add(createContactFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        putBackConnection(connection);

        return  resultCollection;
    }

    @Override
    public void save(Contact contact) {
        String firstName = contact.getFirstName();
        String secondName = contact.getSecondName();
        String lastName = contact.getLastName();
        String dateOfBirth = contact.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sex = contact.getSex().name();
        String nationality = contact.getNationality();
        String martialStatus = contact.getMartialStatus().name();
        String webSite = contact.getWebSite();
        String eMail = contact.geteMail();
        String currentJob = contact.getCurrentJob();
        String photoLink = contact.getPhotoLink();

        String query;
        if (contact.getId() == null) {
            query = "INSERT INTO contact " +
                    "(first_name, " +
                    "second_name, " +
                    "last_name, " +
                    "date_of_birth, " +
                    "sex, " +
                    "nationality, " +
                    "martial_status, " +
                    "web_site, " +
                    "e_mail, " +
                    "current_job, " +
                    "photo_link) " +
                    "VALUES ('" +
                    firstName + "','" +
                    secondName + "','" +
                    lastName + "','" +
                    dateOfBirth + "','" +
                    sex + "','" +
                    nationality + "','" +
                    martialStatus + "','" +
                    webSite + "','" +
                    eMail + "','" +
                    currentJob + "','" +
                    photoLink + "')";
        } else {
            query = "UPDATE contact SET " +
                    "first_name='" + firstName +
                    "', second_name='" + secondName +
                    "', last_name='" + lastName +
                    "', date_of_birth='" + dateOfBirth +
                    "', sex='" + sex +
                    "', nationality='" + nationality +
                    "', martial_status='" + martialStatus +
                    "', web_site='" + webSite +
                    "', e_mail='" + eMail +
                    "', current_job='" + currentJob +
                    "', photo_link='" + photoLink +
                    "' WHERE id=" + contact.getId();
        }
        Integer lastGeneratedValue = executeStatement(query);
        if (contact.getId() == null){
            contact.setId(lastGeneratedValue);
        }

        if (contact.getAttachments() != null) {
            contact.getAttachments().forEach(attachment -> attachmentDao.save(attachment,contact.getId()));
        }
        if (contact.getContactAddress() != null) {
            addressDao.save(contact.getContactAddress(),contact.getId());
        }
        if (contact.getPhoneNumbers() != null) {
            contact.getPhoneNumbers().forEach(phoneNumber -> phoneNumberDao.save(phoneNumber,contact.getId()));
        }

    }

    @Override
    public void delete(int id) {
        executeStatement("DELETE FROM contact WHERE id=" + id);
    }

    private Contact createContactFromResultSet(ResultSet resultSet) throws SQLException {
        Contact resultObject = new Contact();

        resultObject.setId(resultSet.getInt("id"));
        resultObject.setFirstName(resultSet.getString("first_name"));
        resultObject.setSecondName(resultSet.getString("second_name"));
        resultObject.setLastName(resultSet.getString("last_name"));
        resultObject.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
        resultObject.setSex(Sex.valueOf(resultSet.getString("sex")));
        resultObject.setNationality(resultSet.getString("nationality"));
        resultObject.setMartialStatus(MartialStatus.valueOf(resultSet.getString("martial_status")));
        resultObject.setWebSite(resultSet.getString("web_site"));
        resultObject.seteMail(resultSet.getString("e_mail"));
        resultObject.setCurrentJob(resultSet.getString("current_job"));
        resultObject.setPhotoLink(resultSet.getString("photo_link"));
        resultObject.setContactAddress(addressDao.getByContactId(resultObject.getId()));
        resultObject.setPhoneNumbers(phoneNumberDao.getByContactId(resultObject.getId()));
        resultObject.setAttachments(attachmentDao.getByContactId(resultObject.getId()));
        return resultObject;
    }
}
