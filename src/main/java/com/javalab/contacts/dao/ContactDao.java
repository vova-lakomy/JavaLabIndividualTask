package com.javalab.contacts.dao;


import com.javalab.contacts.model.Contact;

public interface ContactDao {

    Contact get(Integer id);

    void save(Contact contact);

    void delete(int id);

}
