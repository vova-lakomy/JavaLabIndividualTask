package com.javalab.contacts.dao;


import com.javalab.contacts.model.PhoneNumber;

import java.sql.Connection;
import java.util.Collection;

public interface PhoneNumberDao {

    PhoneNumber get(Integer id);

    Collection<PhoneNumber> getByContactId(Integer contactId);

    void save(PhoneNumber phoneNumber, Integer contactId);

    void save(PhoneNumber phoneNumber, Integer contactId, Connection connection);

    void delete(Integer id);

}
