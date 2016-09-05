package com.javalab.contacts.dao;


import com.javalab.contacts.model.Contact;

public interface ContactDAO {

    public Contact get(Integer id);

    public Integer save(Contact contact);

    public Boolean delete(int id);

}
