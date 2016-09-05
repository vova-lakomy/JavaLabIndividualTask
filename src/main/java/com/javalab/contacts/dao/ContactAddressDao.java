package com.javalab.contacts.dao;


import com.javalab.contacts.model.ContactAddress;

public interface ContactAddressDao {

    public ContactAddress get(Integer id);

    public void save(ContactAddress contactAddress);

    public void delete(int id);

}
