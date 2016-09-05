package com.javalab.contacts.dao;


import com.javalab.contacts.model.ContactAddress;

public interface ContactAddressDAO {

    public ContactAddress get(Integer id);

    public Integer save(ContactAddress contactAddress);

    public Boolean delete(int id);

}
