package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.ContactAddressDao;
import com.javalab.contacts.model.ContactAddress;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public Collection<ContactAddress> getByContactId(Integer contactId) {
        Connection connection = receiveConnection();
        Collection<ContactAddress> resultCollection = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM contact_address WHERE contact_id=" + contactId);
            while (resultSet.next()){
                resultCollection.add(createAddressFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        putBackConnection(connection);
        return resultCollection;
    }

    @Override
    public void save(ContactAddress contactAddress, Integer contactId) {
        String country = contactAddress.getCountry();
        String town = contactAddress.getTown();
        String street = contactAddress.getStreet();
        int houseNumber = contactAddress.getHouseNumber();
        int flatNumber = contactAddress.getFlatNumber();
        int zipCode = contactAddress.getZipCode();
        String query;
        if (contactAddress.getId() == null) {
            query = "INSERT INTO contact_address " +
                    "(country, " +
                    "town, " +
                    "street, " +
                    "house_number, " +
                    "flat_number, " +
                    "zip_code, " +
                    "contact_id) " +
                    "VALUES ('" +
                    country + "', '" +
                    town + "', '" +
                    street + "', " +
                    houseNumber + ", " +
                    flatNumber + ", " +
                    zipCode + "," +
                    contactId + ")";
        } else {
            query = "UPDATE contact_address SET " +
                    "country='" + country +
                    "', town='" + town +
                    "', street='" + street +
                    "', house_number=" + houseNumber +
                    ", flat_number=" + flatNumber +
                    ", zip_code=" + zipCode +
                    ", contact_id=" + contactId +
                    " WHERE id=" + contactAddress.getId();
        }
        executeStatement(query);
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
