package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.dao.ContactDao;
import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.model.Contact;
import com.javalab.contacts.model.ContactAddress;
import com.javalab.contacts.model.enumerations.MartialStatus;
import com.javalab.contacts.model.enumerations.Sex;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcContactDao implements ContactDao {

    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    private PhoneNumberDao phoneNumberDao = new JdbcPhoneNumberDao();
    private ContactAttachmentDao attachmentDao = new JdbcContactAttachmentDao();

    @Override
    public Contact get(Integer id) {
        PreparedStatement statementGetContact = null;
        Connection connection = connectionManager.receiveConnection();
        Contact resultObject = new Contact();

        try {
            connection.setAutoCommit(false);
            statementGetContact = connection.prepareStatement("SELECT * FROM contact WHERE id= ?");
            statementGetContact.setInt(1,id);
            ResultSet resultSet = statementGetContact.executeQuery();
            while (resultSet.next()){
                resultObject = createContactFromResultSet(resultSet,true);
            }
            resultSet.close();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statementGetContact != null) {
                    statementGetContact.close();
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
    public Collection<Contact> getContactList(){
        PreparedStatement statementGetContactList = null;

        Collection<Contact> resultCollection = new ArrayList<>();
        Connection connection = connectionManager.receiveConnection();
        try {
            statementGetContactList = connection.prepareStatement("SELECT * FROM contact ORDER BY last_name");
            ResultSet resultSet = statementGetContactList.executeQuery();
            while (resultSet.next()){
                resultCollection.add(createContactFromResultSet(resultSet,false));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (statementGetContactList != null) {
                    statementGetContactList.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionManager.putBackConnection(connection);
        }
        return  resultCollection;
    }

    @Override
    public void save(Contact contact) {
        PreparedStatement statementSaveContact = null;
        Connection connection = connectionManager.receiveConnection();
        String queryAddContact;
        if (contact.getId() == null){
            queryAddContact =
                    "INSERT INTO contact " +
                    "(first_name, second_name, last_name, date_of_birth, sex, nationality, martial_status, web_site, " +
                    "e_mail, current_job, photo_link, country, town, street, house_number, flat_number, zip_code) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        } else {
            queryAddContact =
                    "UPDATE contact SET " +
                    "first_name=?, second_name=?, last_name=?, date_of_birth=?, sex=?, nationality=?, " +
                            "martial_status=?, web_site=?, e_mail=?, current_job=?, photo_link=?, country=?, " +
                            "town=?, street=?, house_number=?, flat_number=?, zip_code=? " +
                    "WHERE id=" + contact.getId();
        }

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
        ContactAddress address = contact.getContactAddress();
        String country = address.getCountry();
        String town = address.getTown();
        String street = address.getStreet();
        Integer houseNumber = address.getHouseNumber();
        Integer flatNumber = address.getFlatNumber();
        Integer zipCode = address.getZipCode();
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
            statementSaveContact.setString(12,country);
            statementSaveContact.setString(13,town);
            statementSaveContact.setString(14,street);
            statementSaveContact.setInt(15,houseNumber);
            statementSaveContact.setInt(16,flatNumber);
            statementSaveContact.setInt(17,zipCode);
            statementSaveContact.executeUpdate();

            Integer lastGeneratedValue=null;
            ResultSet generatedKeys = statementSaveContact.getGeneratedKeys();
            if (generatedKeys.next()){
                lastGeneratedValue = generatedKeys.getInt(1);
            }
            generatedKeys.close();
            connection.commit();
            if (contact.getId() == null){
                contact.setId(lastGeneratedValue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (statementSaveContact != null) {
                    statementSaveContact.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                }
            connectionManager.putBackConnection(connection);
        }

        if (contact.getAttachments() != null) {
            contact.getAttachments().forEach(attachment -> attachmentDao.save(attachment,contact.getId()));
        }

        if (contact.getPhoneNumbers() != null) {
            contact.getPhoneNumbers().forEach(phoneNumber -> phoneNumberDao.save(phoneNumber,contact.getId()));
        }

    }

    @Override
    public void delete(Integer id) {
        PreparedStatement statementDeleteContact = null;
        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            statementDeleteContact = connection.prepareStatement("DELETE FROM contact WHERE id= ?");
            statementDeleteContact.setInt(1,id);
            statementDeleteContact.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statementDeleteContact != null) {
                    statementDeleteContact.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionManager.putBackConnection(connection);
        }
    }

    private Contact createContactFromResultSet(ResultSet resultSet, Boolean loadAttachments) throws SQLException {
        Contact resultObject = new Contact();
        ContactAddress address = new ContactAddress();
        address.setCountry(resultSet.getString("country"));
        address.setTown(resultSet.getString("town"));
        address.setStreet(resultSet.getString("street"));
        address.setHouseNumber(resultSet.getInt("house_number"));
        address.setFlatNumber(resultSet.getInt("flat_number"));
        address.setZipCode(resultSet.getInt("zip_code"));

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
        resultObject.setContactAddress(address);
        if (loadAttachments){
            resultObject.setPhoneNumbers(phoneNumberDao.getByContactId(resultObject.getId()));
            resultObject.setAttachments(attachmentDao.getByContactId(resultObject.getId()));
        }
        return resultObject;
    }
}
