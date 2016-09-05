package com.javalab.contacts.dao;


import com.javalab.contacts.model.PhoneNumber;

public interface PhoneNumberDAO {

    public PhoneNumber get(Integer id);

    public Integer save(PhoneNumber phoneNumber);

    public Boolean delete(int id);

}
