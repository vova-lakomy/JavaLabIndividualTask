package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.model.PhoneNumber;
import com.javalab.contacts.model.enumerations.PhoneType;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.javalab.contacts.dao.impl.jdbc.ConnectionProvider.*;


public class JdbcPhoneNumberDao implements PhoneNumberDao {

    public PhoneNumber get(Integer id) {
        String query = "SELECT * FROM contacts.phone_number WHERE id=" + id;
        try {
            Statement statement = receiveConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            //// TODO: 06.09.16
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void save(PhoneNumber phoneNumber) {
        Integer countryCode = phoneNumber.getCountryCode();
        Integer operatorCode = phoneNumber.getOperatorCode();
        Integer number = phoneNumber.getPhoneNumber();
        String phoneType = phoneNumber.getPhoneType().name();
        String phoneComment = phoneNumber.getPhoneComment();
        String query;

        if (phoneNumber.getId() == null) {
            query = "INSERT INTO contacts.phone_number " +
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
            query = "UPDATE contacts.phone_number SET " +
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
        executeStatement("DELETE FROM contacts.phone_number WHERE id=" + id);
    }

    private void executeStatement(String query){
        try {
            Statement statement = receiveConnection().createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        JdbcPhoneNumberDao dao = new JdbcPhoneNumberDao();
        dao.save(new PhoneNumber(null,370,25,7571231,PhoneType.MOBILE,"qwecefcefcdxfcer"));
        dao.save(new PhoneNumber(1,375,2323,71231,PhoneType.HOME,"qwer2"));
        dao.delete(5);
        System.out.println(dao.get(6));
    }
}
