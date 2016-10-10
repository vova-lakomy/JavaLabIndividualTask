package com.javalab.contacts.repository.impl;

import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcPhoneNumberDao;
import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.repository.PhoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

import static com.javalab.contacts.dao.impl.jdbc.ConnectionManager.closeConnection;
import static com.javalab.contacts.dao.impl.jdbc.ConnectionManager.receiveConnection;


public class PhoneRepositoryImpl implements PhoneRepository {
    private static final Logger logger = LoggerFactory.getLogger(PhoneRepositoryImpl.class);
    private PhoneNumberDao numberDao = new JdbcPhoneNumberDao();


    @Override
    public void delete(Integer id) throws ConnectionFailedException {
        logger.debug("deleting PhoneNumber by id={}", id);
        Connection connection = receiveConnection();
        numberDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            logger.debug("transaction opened");
            numberDao.delete(id);
            connection.commit();
            logger.debug("transaction closed");
        } catch (SQLException e) {
            logger.error("",e);
            try {
                logger.error("error while deleting phone number, start changes rollback");
                connection.rollback();
                logger.error("rollback done");
            } catch (SQLException e1) {
                logger.error("",e);
            }
            throw new ConnectionFailedException();
        } finally {
            closeConnection(connection);
        }

    }
}
