package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.ContactAddressDao;
import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.dao.ContactDao;
import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.model.Contact;
import com.javalab.contacts.model.enumerations.MartialStatus;
import com.javalab.contacts.model.enumerations.Sex;
import com.mysql.jdbc.JDBC4PreparedStatement;

import java.sql.*;
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
        PreparedStatement statementGetContact = null;
        String queryGetContact = "SELECT * FROM contact WHERE id= ?";
        Connection connection = receiveConnection();
        Contact resultObject = new Contact();

        try {
            connection.setAutoCommit(false);
            statementGetContact = connection.prepareStatement(queryGetContact);
            statementGetContact.setInt(1,id);
            ResultSet resultSet = statementGetContact.executeQuery();
            while (resultSet.next()){
                resultObject = createContactFromResultSet(resultSet);
            }
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                statementGetContact.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            putBackConnection(connection);
        }
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
        PreparedStatement statementSaveContact = null;
        Connection connection = receiveConnection();
        String queryAddContact = "";
        if (contact.getId() == null){
            queryAddContact =
                    "INSERT INTO contact " +
                    "(first_name, second_name, last_name, date_of_birth, sex, nationality, martial_status, web_site, " +
                    "e_mail, current_job, photo_link) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        } else {
            queryAddContact =
                    "UPDATE contact SET " +
                    "first_name=?, second_name=?, last_name=?, date_of_birth=?, sex=?, nationality=?, " +
                    "martial_status=?, web_site=?, e_mail=?, current_job=?, photo_link=? " +
                    "WHERE id=" + contact.getId();
        }
        Integer lastGeneratedValue=null;

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


        try {
            connection.setAutoCommit(false);
            statementSaveContact = connection.prepareStatement(queryAddContact,Statement.RETURN_GENERATED_KEYS);
            statementSaveContact.setString(1,firstName);
            statementSaveContact.setString(2,secondName);
            statementSaveContact.setString(3,lastName);
            statementSaveContact.setString(4,dateOfBirth);
            statementSaveContact.setString(5,sex);
            statementSaveContact.setString(6,nationality);
            statementSaveContact.setString(7,martialStatus);
            statementSaveContact.setString(8,webSite);
            statementSaveContact.setString(9,eMail);
            statementSaveContact.setString(10,currentJob);
            statementSaveContact.setString(11,photoLink);
            statementSaveContact.executeUpdate();
            ResultSet generatedKeys = statementSaveContact.getGeneratedKeys();
            if (generatedKeys.next()){
                lastGeneratedValue = generatedKeys.getInt(1);
            }
            generatedKeys.close();
            if (contact.getId() == null){
                contact.setId(lastGeneratedValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                statementSaveContact.close();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            putBackConnection(connection);
        }

        if (contact.getContactAddress() != null) {
            addressDao.save(contact.getContactAddress(),contact.getId());
        }

        if (contact.getAttachments() != null) {
            contact.getAttachments().forEach(attachment -> attachmentDao.save(attachment,contact.getId()));
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
