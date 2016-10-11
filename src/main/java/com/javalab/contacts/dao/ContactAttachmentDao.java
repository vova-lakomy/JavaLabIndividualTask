package com.javalab.contacts.dao;


import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.model.ContactAttachment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public interface ContactAttachmentDao {

    ContactAttachment get(Integer id) throws ConnectionFailedException;

    Collection<ContactAttachment> getByContactId(Integer contactId) throws ConnectionFailedException;

    void save(ContactAttachment contactAttachment, Integer contactId) throws SQLException;
    
    void delete(Integer id) throws ConnectionFailedException;

    void setConnection(Connection connection);
}
