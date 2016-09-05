package com.javalab.contacts.dao;


import com.javalab.contacts.model.Contact;

public interface ContactDao {

    public Contact get(Integer id);

    public void save(Contact contact);

    public void delete(int id);

}
