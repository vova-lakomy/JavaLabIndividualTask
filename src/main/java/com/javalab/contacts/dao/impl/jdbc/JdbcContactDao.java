package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.ContactAddressDao;
import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.dao.ContactDao;
import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.model.Contact;
import com.javalab.contacts.model.ContactAddress;
import com.javalab.contacts.model.ContactAttachment;
import com.javalab.contacts.model.PhoneNumber;
import com.javalab.contacts.model.enumerations.Sex;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import static com.javalab.contacts.dao.impl.jdbc.DbConnectionProvider.putBackConnection;
import static com.javalab.contacts.dao.impl.jdbc.DbConnectionProvider.receiveConnection;


public class JdbcContactDao implements ContactDao {

    private ContactAddressDao addressDao = new JdbcContactAddressDao();
    private PhoneNumberDao phoneNumberDao = new JdbcPhoneNumberDao();
    private ContactAttachmentDao attachmentDao = new JdbcContactAttachmentDao();

    public Contact get(Integer id) {
        Connection connection = receiveConnection();
        Contact resultObject = new Contact();
        String query = "SELECT * FROM contact WHERE id=" + id;
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                resultObject.setId(resultSet.getInt("id"));
                resultObject.setFirstName(resultSet.getString("first_name"));
                resultObject.setSecondName(resultSet.getString("second_name"));
                resultObject.setLastName(resultSet.getString("last_name"));
                resultObject.setDateOfBirth(resultSet.getDate("date_of_birth").toLocalDate());
                resultObject.setSex(Sex.valueOf(resultSet.getString("sex")));
                resultObject.setNationality(resultSet.getString("nationality"));
                resultObject.setWebSite(resultSet.getString("web_site"));
                resultObject.seteMail(resultSet.getString("e_mail"));
                resultObject.setCurrentJob(resultSet.getString("current_job"));
                resultObject.setPhotoLink(resultSet.getString("photo_link"));
                resultObject.setContactAddress(addressDao.get(resultSet.getInt("contact_address_id")));
                resultObject.setPhoneNumber(phoneNumberDao.get(resultSet.getInt("phone_number_id")));
                resultObject.setAttachments(attachmentDao.getByContactId(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        putBackConnection(connection);
        return resultObject.getId() != null ? resultObject : null;
    }

    public void save(Contact contact) {
        String firstName = contact.getFirstName();
        String secondName = contact.getSecondName();
        String lastName = contact.getLastName();
        String dateOfBirth = contact.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String sex = contact.getSex().name();
        String nationality = contact.getNationality();
        String webSite = contact.getWebSite();
        String mail = contact.geteMail();
        String currentJob = contact.getCurrentJob();
        String photoLink = contact.getPhotoLink();
        Collection<ContactAttachment> attachments = contact.getAttachments();
        ContactAddress contactAddress = contact.getContactAddress();
        PhoneNumber phoneNumber = contact.getPhoneNumber();


    }

    public void delete(int id) {

    }
}
