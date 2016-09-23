package com.javalab.contacts.dao;


import com.javalab.contacts.dto.ContactSearchDTO;
import com.javalab.contacts.model.Contact;

import java.util.Collection;

public interface ContactDao {

    Contact get(Integer id);

    Contact getContactShortInfo(Integer contactId);

    Collection<Contact> getContactList(int pageNumber);

    Collection<Contact> search(ContactSearchDTO searchObject, int pageNumber);

    Integer save(Contact contact);

    void delete(Integer id);

    int getRowsPerPageCount();

    void setRowsPerPageCount(int rowsCount);

    int getNumberOfRecordsFound();
}
