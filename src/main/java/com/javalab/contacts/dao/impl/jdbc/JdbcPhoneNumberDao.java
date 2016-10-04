package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.model.PhoneNumber;
import com.javalab.contacts.model.enumerations.PhoneType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcPhoneNumberDao implements PhoneNumberDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcPhoneNumberDao.class);
    private ConnectionManager connectionManager = ConnectionManager.getInstance();

    public PhoneNumber get(Integer id) {
        logger.debug("try to get phone number by id=" + id);
        PreparedStatement statementGetPhoneNumber = null;
        Connection connection = connectionManager.receiveConnection();
        PhoneNumber resultObject = new PhoneNumber();
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            statementGetPhoneNumber = connection.prepareStatement("SELECT * FROM phone_number WHERE id= ?");
            statementGetPhoneNumber.setInt(1, id);
            ResultSet resultSet = statementGetPhoneNumber.executeQuery();
            while (resultSet.next()) {
                resultObject = createPhoneNumberFromResultSet(resultSet);
            }
            resultSet.close();
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            logger.error("{}", e);
        } finally {
            try {
                if (statementGetPhoneNumber != null) {
                    statementGetPhoneNumber.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("{}", e);
            }
            connectionManager.putBackConnection(connection);
        }
        if (resultObject.getId() != null){
            return resultObject;
        }
        else {
            return null;
        }
    }

    @Override
    public Collection<PhoneNumber> getByContactId(Integer contactId) {
        logger.debug("try to get phone number by contact id= " + contactId);
        Connection connection = connectionManager.receiveConnection();
        Collection<PhoneNumber> resultCollection = new ArrayList<>();
        PreparedStatement statementGetByContactId = null;
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            statementGetByContactId = connection.prepareStatement("SELECT * FROM phone_number WHERE contact_id=?");
            statementGetByContactId.setInt(1, contactId);
            ResultSet resultSet = statementGetByContactId.executeQuery();
            while (resultSet.next()) {
                resultCollection.add(createPhoneNumberFromResultSet(resultSet));
            }
            resultSet.close();
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            logger.error("{}", e);
        } finally {
            try {
                if (statementGetByContactId != null) {
                    statementGetByContactId.close();
                }
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                logger.error("{}", e);
            }
            connectionManager.putBackConnection(connection);
        }
        return resultCollection;
    }

    public void save(PhoneNumber phoneNumber, Integer contactId) {

        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            save(phoneNumber, contactId, connection);
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            logger.error("{}", e);
            try {
                logger.debug("transaction rolled back");
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("{}", ex);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("{}", e);
            }
            connectionManager.putBackConnection(connection);
        }
    }

    @Override
    public void save(PhoneNumber phoneNumber, Integer contactId, Connection connection) {
        logger.debug("saving phone number id= " + phoneNumber.getId() + " contact id= " + contactId);
        PreparedStatement statementSavePhoneNumber = null;
        String savePhoneQuery = defineSavePhoneQuery(phoneNumber.getId());
        try {
            statementSavePhoneNumber = connection.prepareStatement(savePhoneQuery);
            setSaveStatementParams(statementSavePhoneNumber, phoneNumber, contactId);
            statementSavePhoneNumber.executeUpdate();
        } catch (SQLException e) {
            logger.error("{}", e);
        } finally {
            try {
                if (statementSavePhoneNumber != null) {
                    statementSavePhoneNumber.close();
                }
            } catch (Exception e) {
                logger.error("{}", e);
            }
        }
    }

    public void delete(Integer id) {
        logger.debug("deleting phone number with id= " + id);
        PreparedStatement statementDeletePhoneNumber = null;
        Connection connection = connectionManager.receiveConnection();
        try {
            connection.setAutoCommit(false);
            logger.debug("opened transaction");
            statementDeletePhoneNumber = connection.prepareStatement("DELETE FROM phone_number WHERE id= ?");
            statementDeletePhoneNumber.setInt(1, id);
            statementDeletePhoneNumber.executeUpdate();
            connection.commit();
            logger.debug("closed transaction");
        } catch (SQLException e) {
            logger.error("{}", e);
        } finally {
            try {
                if (statementDeletePhoneNumber != null) {
                    statementDeletePhoneNumber.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.error("{}", e);
            }
            connectionManager.putBackConnection(connection);
        }
    }

    private PhoneNumber createPhoneNumberFromResultSet(ResultSet resultSet) throws SQLException {
        logger.debug("creating 'PhoneNumber' entity from resultSet");
        PhoneNumber resultObject = new PhoneNumber();
        resultObject.setId(resultSet.getInt("id"));
        resultObject.setCountryCode(resultSet.getInt("country_code"));
        resultObject.setOperatorCode(resultSet.getInt("operator_code"));
        resultObject.setPhoneNumber(resultSet.getInt("phone_number"));
        resultObject.setPhoneType(PhoneType.valueOf(resultSet.getString("phone_type")));
        resultObject.setPhoneComment(resultSet.getString("phone_comment"));
        logger.debug("created {}", resultObject);
        return resultObject;
    }

    private String defineSavePhoneQuery(Integer phoneNumberId) {
        logger.debug("defining save phone query");
        if (phoneNumberId == null) {
            return "INSERT INTO phone_number " +
                    "(country_code, operator_code, phone_number, phone_type, phone_comment, contact_id) " +
                    "VALUES (?,?,?,?,?,?)";
        } else {
            return "UPDATE phone_number SET " +
                    "country_code=?, operator_code=?, phone_number=?, phone_type=?, phone_comment=?, contact_id=? " +
                    "WHERE id=" + phoneNumberId;
        }
    }

    private void setSaveStatementParams(PreparedStatement statement, PhoneNumber phoneNumber, Integer contactId) throws SQLException {
        logger.debug("setting params to {}", statement);
        statement.setObject(1, phoneNumber.getCountryCode());
        statement.setObject(2, phoneNumber.getOperatorCode());
        statement.setObject(3, phoneNumber.getPhoneNumber());
        String phoneType = null;
        if (phoneNumber.getPhoneType() != null) {
            phoneType = phoneNumber.getPhoneType().name();
        }
        statement.setString(4, phoneType);
        statement.setString(5, phoneNumber.getPhoneComment());
        statement.setObject(6, contactId);
    }
}
