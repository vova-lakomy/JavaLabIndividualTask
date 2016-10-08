package com.javalab.contacts.repository.impl;

import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcPhoneNumberDao;
import com.javalab.contacts.exception.ConnectionDeniedException;
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
    public void delete(Integer id) throws ConnectionDeniedException {
        Connection connection = receiveConnection();
        numberDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            numberDao.delete(id);
            connection.commit();
        } catch (SQLException e) {
            logger.error("",e);
        } finally {
            closeConnection(connection);
        }

    }
}
