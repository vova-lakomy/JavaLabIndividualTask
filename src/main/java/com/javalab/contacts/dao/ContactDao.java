package com.javalab.contacts.dao;


import com.javalab.contacts.model.Contact;

import java.util.Collection;

public interface ContactDao {

    Contact get(Integer id);

    Contact getContactShortInfo(Integer contactId);

    Collection<Contact> getContactList();

    void save(Contact contact);

    void delete(Integer id);
}
