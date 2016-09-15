package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.model.PhoneNumber;
import com.javalab.contacts.model.enumerations.PhoneType;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcPhoneNumberDao implements PhoneNumberDao {

    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    public PhoneNumber get(Integer id) {
        PreparedStatement statementGetPhoneNumber = null;
        Connection connection = connectionManager.receiveConnection();
        PhoneNumber resultObject = new PhoneNumber();
        try {
            connection.setAutoCommit(false);
            statementGetPhoneNumber = connection.prepareStatement("SELECT * FROM phone_number WHERE id= ?");
            statementGetPhoneNumber.setInt(1, id);
            ResultSet resultSet = statementGetPhoneNumber.executeQuery();
            while (resultSet.next()) {
                resultObject = createPhoneNumberFromResultSet(resultSet);
            }
            resultSet.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statementGetPhoneNumber != null) {
                    statementGetPhoneNumber.close();
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
    public Collection<PhoneNumber> getByContactId(Integer contactId) {
        Connection connection = connectionManager.receiveConnection();
        Collection<PhoneNumber> resultCollection = new ArrayList<>();
        PreparedStatement statementGetByContactId = null;
        try {
            connection.setAutoCommit(false);
            statementGetByContactId = connection.prepareStatement("SELECT * FROM phone_number WHERE contact_id=?");
            statementGetByContactId.setInt(1, contactId);
            ResultSet resultSet = statementGetByContactId.executeQuery();
            while (resultSet.next()) {
                resultCollection.add(createPhoneNumberFromResultSet(resultSet));
            }
            resultSet.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statementGetByContactId != null) {
                    statementGetByContactId.close();
                }
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionManager.putBackConnection(connection);
        }
        return resultCollection;
    }

    public void save(PhoneNumber phoneNumber, Integer contactId){

        PreparedStatement statementSavePhoneNumber = null;
        Connection connection = connectionManager.receiveConnection();
        String queryAddPhoneNumber = "";
        if (phoneNumber.getId() == null){
            queryAddPhoneNumber =
                    "INSERT INTO phone_number " +
                            "(country_code, operator_code, phone_number, phone_type, phone_comment, contact_id) " +
                            "VALUES (?,?,?,?,?,?)";
        } else {
            queryAddPhoneNumber =
                    "UPDATE phone_number SET " +
                            "country_code=?, operator_code=?, phone_number=?, phone_type=?, phone_comment=?, contact_id=? " +
                            "WHERE id=" + phoneNumber.getId();
        }
        Integer countryCode = phoneNumber.getCountryCode();
        Integer operatorCode = phoneNumber.getOperatorCode();
        Integer number = phoneNumber.getPhoneNumber();
        String phoneType = phoneNumber.getPhoneType().name();
        String phoneComment = phoneNumber.getPhoneComment();

        try {
            connection.setAutoCommit(false);
            statementSavePhoneNumber = connection.prepareStatement(queryAddPhoneNumber);
            statementSavePhoneNumber.setInt(1,countryCode);
            statementSavePhoneNumber.setInt(2,operatorCode);
            statementSavePhoneNumber.setInt(3,number);
            statementSavePhoneNumber.setString(4,phoneType);
            statementSavePhoneNumber.setString(5,phoneComment);
            statementSavePhoneNumber.setInt(6,contactId);
            statementSavePhoneNumber.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statementSavePhoneNumber != null) {
                    statementSavePhoneNumber.close();
                }
                connection.setAutoCommit(true);
            }catch (Exception e){
                e.printStackTrace();
            }
            connectionManager.putBackConnection(connection);
        }
    }

    public void delete(Integer id) {
        PreparedStatement statementDeletePhoneNumber = null;
        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            statementDeletePhoneNumber = connection.prepareStatement("DELETE FROM phone_number WHERE id= ?");
            statementDeletePhoneNumber.setInt(1,id);
            statementDeletePhoneNumber.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (statementDeletePhoneNumber != null) {
                    statementDeletePhoneNumber.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionManager.putBackConnection(connection);
        }
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
