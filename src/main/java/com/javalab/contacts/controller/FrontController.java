package com.javalab.contacts.controller;


import com.javalab.contacts.dao.ContactDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactDao;
import com.javalab.contacts.model.Contact;


import java.util.Collection;


public class FrontController {
    private ContactDao contactDao = new JdbcContactDao();

    public Collection<Contact> getAllContacts(){
        return contactDao.getAllContacts();
    }
}
