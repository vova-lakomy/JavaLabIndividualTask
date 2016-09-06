package com.javalab.contacts.dao;


import com.javalab.contacts.model.PhoneNumber;

public interface PhoneNumberDao {

    PhoneNumber get(Integer id);

    PhoneNumber getByContactId(Integer contactId);

    void save(PhoneNumber phoneNumber, Integer contactId);

    void delete(int id);

}
