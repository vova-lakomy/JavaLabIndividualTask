package com.javalab.contacts.dao;


import com.javalab.contacts.model.ContactAttachment;

public interface ContactAttachmentDao {

    public ContactAttachment get(Integer id);

    public void save(ContactAttachment contactAttachment);

    public void delete(int id);

}
