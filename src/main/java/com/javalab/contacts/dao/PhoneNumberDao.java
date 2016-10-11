package com.javalab.contacts.dao;


import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.model.PhoneNumber;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public interface PhoneNumberDao {

    PhoneNumber get(Integer id) throws ConnectionFailedException;

    Collection<PhoneNumber> getByContactId(Integer contactId) throws ConnectionFailedException;

    void save(PhoneNumber phoneNumber, Integer contactId) throws SQLException;

    void delete(Integer id) throws ConnectionFailedException;

    void setConnection(Connection connection);
}
