package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.model.PhoneNumber;
import com.javalab.contacts.model.enumerations.PhoneType;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import static com.javalab.contacts.dao.impl.jdbc.DbConnectionProvider.*;
import static com.javalab.contacts.dao.impl.jdbc.StatementExecutor.executeStatement;


public class JdbcPhoneNumberDao implements PhoneNumberDao {

    public PhoneNumber get(Integer id) {
        Connection connection = receiveConnection();
        PhoneNumber resultObject = new PhoneNumber();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM phone_number WHERE id=" + id);
            while (resultSet.next()){
                resultObject = createPhoneNumberFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        putBackConnection(connection);
        return resultObject.getId() != null ? resultObject : null;
    }

    @Override
    public Collection<PhoneNumber> getByContactId(Integer contactId) {
        Connection connection = receiveConnection();
        Collection <PhoneNumber> resultCollection = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM phone_number WHERE contact_id=" + contactId);
            while (resultSet.next()){
                resultCollection.add(createPhoneNumberFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        putBackConnection(connection);
        return resultCollection;
    }

    public void save(PhoneNumber phoneNumber, Integer contactId) {
        Integer countryCode = phoneNumber.getCountryCode();
        Integer operatorCode = phoneNumber.getOperatorCode();
        Integer number = phoneNumber.getPhoneNumber();
        String phoneType = phoneNumber.getPhoneType().name();
        String phoneComment = phoneNumber.getPhoneComment();
        String query;

        if (phoneNumber.getId() == null) {
            query = "INSERT INTO phone_number " +
                    "(country_code, " +
                    "operator_code, " +
                    "phone_number, " +
                    "phone_type, " +
                    "phone_comment, " +
                    "contact_id) " +
                    "VALUES (" +
                    countryCode + "," +
                    operatorCode + "," +
                    number + ", '" +
                    phoneType + "','" +
                    phoneComment + "'," +
                    contactId + ")";
        } else {
            query = "UPDATE phone_number SET " +
                    "country_code=" + countryCode +
                    ", operator_code=" + operatorCode +
                    ", phone_number=" + number +
                    ", phone_type='" + phoneType +
                    "', phone_comment='" + phoneComment +
                    "' WHERE id=" + phoneNumber.getId();
        }
        executeStatement(query);
    }

    public void delete(int id) {
        executeStatement("DELETE FROM phone_number WHERE id=" + id);
    }

    private PhoneNumber createPhoneNumberFromResultSet(ResultSet resultSet) throws SQLException {
        PhoneNumber resultObject = new PhoneNumber();
        resultObject.setId(resultSet.getInt("id"));
        resultObject.setCountryCode(resultSet.getInt("country_code"));
        resultObject.setOperatorCode(resultSet.getInt("operator_code"));
        resultObject.setPhoneNumber(resultSet.getInt("phone_number"));
        resultObject.setPhoneType(PhoneType.valueOf(resultSet.getString("phone_type")));
        resultObject.setPhoneComment(resultSet.getString("phone_comment"));
        return resultObject;
    }
}
