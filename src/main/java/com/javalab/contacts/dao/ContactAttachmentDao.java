package com.javalab.contacts.dao;


import com.javalab.contacts.model.ContactAttachment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public interface ContactAttachmentDao {

    ContactAttachment get(Integer id);

    Collection<ContactAttachment> getByContactId(Integer contactId);

    void save(ContactAttachment contactAttachment, Integer contactId) throws SQLException;
    
    void delete(Integer id);

    void setConnection(Connection connection);
}
