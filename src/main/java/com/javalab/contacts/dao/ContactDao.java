package com.javalab.contacts.dao;


import com.javalab.contacts.model.Contact;

import java.sql.SQLException;
import java.util.Collection;

public interface ContactDao {

    Contact get(Integer id);

    Collection<Contact> getAllContacts();

    void save(Contact contact);

    void delete(int id);

}
