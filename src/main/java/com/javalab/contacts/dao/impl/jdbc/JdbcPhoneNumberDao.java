package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.model.PhoneNumber;
import com.javalab.contacts.model.enumerations.PhoneType;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.javalab.contacts.dao.impl.jdbc.DbWorker.*;


public class JdbcPhoneNumberDao implements PhoneNumberDao {

    public PhoneNumber get(Integer id) {
        PhoneNumber resultObject = new PhoneNumber();
        String query = "SELECT * FROM phone_number WHERE id=" + id;
        try {
            Statement statement = receiveConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                resultObject.setId(resultSet.getInt("id"));
                resultObject.setCountryCode(resultSet.getInt("country_code"));
                resultObject.setOperatorCode(resultSet.getInt("operator_code"));
                resultObject.setPhoneNumber(resultSet.getInt("phone_number"));
                resultObject.setPhoneType(PhoneType.valueOf(resultSet.getString("phone_type")));
                resultObject.setPhoneComment(resultSet.getString("phone_comment"));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultObject;
    }

    public void save(PhoneNumber phoneNumber) {
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
                    "phone_comment) " +
                    "VALUES (" +
                    countryCode + "," +
                    operatorCode + "," +
                    number + ", '" +
                    phoneType + "','" +
                    phoneComment + "')";
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
}
