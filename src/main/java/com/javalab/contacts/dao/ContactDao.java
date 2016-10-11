package com.javalab.contacts.dao;


import com.javalab.contacts.dto.ContactSearchDTO;
import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.exception.EntityNotFoundException;
import com.javalab.contacts.exception.EntityPersistException;
import com.javalab.contacts.model.Contact;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public interface ContactDao {

    Contact get(Integer id) throws EntityNotFoundException, ConnectionFailedException;

    Collection<Contact> getByDayAndMonth(Integer day, Integer month) throws ConnectionFailedException;

    Collection<Contact> getContactList(int pageNumber) throws ConnectionFailedException;

    Collection<Contact> search(ContactSearchDTO searchObject, int pageNumber) throws ConnectionFailedException;

    Integer save(Contact contact) throws EntityPersistException;

    void delete(Integer id) throws ConnectionFailedException;

    void setPersonalLink(String personalLink, Integer id) throws SQLException;

    Integer getRowsPerPageCount();

    void setRowsPerPageCount(int rowsCount);

    Integer getNumberOfRecordsFound();

    String getPersonalLink(Integer id) throws ConnectionFailedException;

    void setConnection(Connection connection);
}
