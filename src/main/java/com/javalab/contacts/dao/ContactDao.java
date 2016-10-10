package com.javalab.contacts.dao;


import com.javalab.contacts.dto.ContactSearchDTO;
import com.javalab.contacts.exception.EntityNotFoundException;
import com.javalab.contacts.model.Contact;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public interface ContactDao {

    Contact get(Integer id) throws EntityNotFoundException;

    Collection<Contact> getByDayAndMonth(Integer day, Integer month);

    Collection<Contact> getContactList(int pageNumber);

    Collection<Contact> search(ContactSearchDTO searchObject, int pageNumber);

    Integer save(Contact contact);

    void delete(Integer id);

    void setPersonalLink(String personalLink, Integer id) throws SQLException;

    Integer getRowsPerPageCount();

    void setRowsPerPageCount(int rowsCount);

    Integer getNumberOfRecordsFound();

    String getPersonalLink(Integer id);

    void setConnection(Connection connection);
}
