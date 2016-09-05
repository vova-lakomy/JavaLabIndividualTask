package com.javalab.contacts.dao;


import com.javalab.contacts.model.ContactAttachment;

public interface ContactAttachmentDAO {

    public ContactAttachment get(Integer id);

    public Integer save(ContactAttachment contactAttachment);

    public Boolean delete(int id);

}
