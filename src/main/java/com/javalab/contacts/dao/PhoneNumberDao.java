package com.javalab.contacts.dao;


import com.javalab.contacts.model.PhoneNumber;

public interface PhoneNumberDao {

    public PhoneNumber get(Integer id);

    public void save(PhoneNumber phoneNumber);

    public void delete(int id);

}
