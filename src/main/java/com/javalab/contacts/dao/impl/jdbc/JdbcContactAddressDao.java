package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.ContactAddressDao;
import com.javalab.contacts.model.ContactAddress;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import static com.javalab.contacts.dao.impl.jdbc.DbConnectionProvider.putBackConnection;
import static com.javalab.contacts.dao.impl.jdbc.DbConnectionProvider.receiveConnection;
import static com.javalab.contacts.dao.impl.jdbc.StatementExecutor.executeStatement;


public class JdbcContactAddressDao implements ContactAddressDao {


    @Override
    public ContactAddress get(Integer id) {
        Connection connection = receiveConnection();
        ContactAddress resultObject = new ContactAddress();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM contact_address WHERE id=" + id);
            while (resultSet.next()){
                resultObject = createAddressFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        putBackConnection(connection);
        return resultObject.getId() != null ? resultObject : null;
    }

    @Override
    public ContactAddress getByContactId(Integer contactId) {
        Connection connection = receiveConnection();
//        Collection<ContactAddress> resultCollection = new ArrayList<>();
        ContactAddress resultObject = new ContactAddress();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM contact_address WHERE contact_id=" + contactId);
            while (resultSet.next()){
                resultObject = createAddressFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        putBackConnection(connection);
        return resultObject;
    }

    @Override
    public void save(ContactAddress contactAddress, Integer contactId) {
        PreparedStatement statementSaveAddress = null;
        Connection connection = receiveConnection();
        String querySaveAddress;
        if (contactAddress.getId() == null){
            querySaveAddress =
                    "INSERT INTO contact_address " +
                    "(country, town, street, house_number, flat_number, zip_code, contact_id) " +
                    "VALUES (?,?,?,?,?,?,?)";
        } else {
            querySaveAddress = "UPDATE contact_address SET " +
                    "country=?, town=?, street=?, house_number=?, flat_number=?, zip_code=?, contact_id=? " +
                    "WHERE id=" + contactAddress.getId();
        }


        String country = contactAddress.getCountry();
        String town = contactAddress.getTown();
        String street = contactAddress.getStreet();
        int houseNumber = contactAddress.getHouseNumber();
        int flatNumber = contactAddress.getFlatNumber();
        int zipCode = contactAddress.getZipCode();

        try {
            connection.setAutoCommit(false);
            statementSaveAddress = connection.prepareStatement(querySaveAddress);
            statementSaveAddress.setString(1,country);
            statementSaveAddress.setString(2,town);
            statementSaveAddress.setString(3,street);
            statementSaveAddress.setInt(4,houseNumber);
            statementSaveAddress.setInt(5,flatNumber);
            statementSaveAddress.setInt(6,zipCode);
            statementSaveAddress.setInt(7,contactId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statementSaveAddress.close();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            putBackConnection(connection);
        }
    }

    @Override
    public void save(ContactAddress contactAddress, Integer contactId, Connection connection) {
//        PreparedStatement statementSaveAddress = null;
//        String querySaveAddress = "INSERT INTO contact_address " +
//                "(country, town, street, house_number, flat_number, zip_code, contact_id) " +
//                "VALUES (?,?,?,?,?,?,?)";
//
//        String country = contactAddress.getCountry();
//        String town = contactAddress.getTown();
//        String street = contactAddress.getStreet();
//        int houseNumber = contactAddress.getHouseNumber();
//        int flatNumber = contactAddress.getFlatNumber();
//        int zipCode = contactAddress.getZipCode();
//
//        try {
//            statementSaveAddress = connection.prepareStatement(querySaveAddress);
//            statementSaveAddress.setString(1,country);
//            statementSaveAddress.setString(2,town);
//            statementSaveAddress.setString(3,street);
//            statementSaveAddress.setInt(4,houseNumber);
//            statementSaveAddress.setInt(5,flatNumber);
//            statementSaveAddress.setInt(6,zipCode);
//            statementSaveAddress.setInt(7,contactId);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                statementSaveAddress.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//

    }

    @Override
    public void delete(int id) {
        executeStatement("DELETE FROM contact_address WHERE id=" + id);
    }

    private ContactAddress executeQuery(String query){
        Connection connection = receiveConnection();
        ContactAddress resultObject = new ContactAddress();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                resultObject = createAddressFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        putBackConnection(connection);
        return resultObject.getId() != null ? resultObject : null;
    }

    private ContactAddress createAddressFromResultSet(ResultSet resultSet) throws SQLException {
        ContactAddress resultObject = new ContactAddress();
        resultObject.setId(resultSet.getInt("id"));
        resultObject.setCountry(resultSet.getString("country"));
        resultObject.setTown(resultSet.getString("town"));
        resultObject.setStreet(resultSet.getString("street"));
        resultObject.setHouseNumber(resultSet.getInt("house_number"));
        resultObject.setFlatNumber(resultSet.getInt("flat_number"));
        resultObject.setZipCode(resultSet.getInt("zip_code"));
        return resultObject;
    }
}
