package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.ContactAddressDao;
import com.javalab.contacts.model.ContactAddress;
import com.javalab.contacts.model.PhoneNumber;
import com.javalab.contacts.model.enumerations.PhoneType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.javalab.contacts.dao.impl.jdbc.DbWorker.receiveConnection;


public class JdbcContactAddressDao implements ContactAddressDao {

    public ContactAddress get(Integer id) {
        ContactAddress resultObject = new ContactAddress();
//        String query = "SELECT * FROM contacts_vladimir_lakomy.phone_number WHERE id=" + id;
//        try {
//            Statement statement = receiveConnection().createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//            while (resultSet.next()){
//                resultObject.setId(resultSet.getInt("id"));
//                resultObject.setCountryCode(resultSet.getInt("country_code"));
//                resultObject.setOperatorCode(resultSet.getInt("operator_code"));
//                resultObject.setPhoneNumber(resultSet.getInt("phone_number"));
//                resultObject.setPhoneType(PhoneType.valueOf(resultSet.getString("phone_type")));
//                resultObject.setPhoneComment(resultSet.getString("phone_comment"));
//            }
//            statement.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        return resultObject;
    }

    public void save(ContactAddress contactAddress) {

    }

    public void delete(int id) {

    }
}
