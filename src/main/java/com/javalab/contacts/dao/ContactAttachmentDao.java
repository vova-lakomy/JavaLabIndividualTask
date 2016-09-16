package com.javalab.contacts.dao;


import com.javalab.contacts.model.ContactAttachment;

import java.sql.Connection;
import java.util.Collection;

public interface ContactAttachmentDao {

    ContactAttachment get(Integer id);

    Collection<ContactAttachment> getByContactId(Integer contactId);

    void save(ContactAttachment contactAttachment, Integer contactId);

    void save(ContactAttachment contactAttachment, Integer contactId, Connection connection);

    void delete(Integer id);

}
