package com.javalab.contacts.dao;


import com.javalab.contacts.model.ContactAttachment;

import java.util.Collection;

public interface ContactAttachmentDao {

    ContactAttachment get(Integer id);

    Collection<ContactAttachment> getByContactId(Integer contactId);

    void save(ContactAttachment contactAttachment, Integer contactId);

    void delete(Integer id);

}
