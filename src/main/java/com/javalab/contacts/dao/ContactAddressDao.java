package com.javalab.contacts.dao;


import com.javalab.contacts.model.ContactAddress;

import java.sql.Connection;
import java.util.Collection;

public interface ContactAddressDao {

    ContactAddress get(Integer id);

    ContactAddress getByContactId(Integer contactId);

    void save(ContactAddress contactAddress, Integer contactId);

    void save(ContactAddress contactAddress, Integer contactId, Connection connection);

    void delete(int id);

}
