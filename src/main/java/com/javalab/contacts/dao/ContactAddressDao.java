package com.javalab.contacts.dao;


import com.javalab.contacts.model.ContactAddress;

public interface ContactAddressDao {

    ContactAddress get(Integer id);

    ContactAddress getByContactId(Integer contactId);

    void save(ContactAddress contactAddress, Integer contactId);

    void delete(int id);

}
