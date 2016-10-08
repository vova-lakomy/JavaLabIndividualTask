package com.javalab.contacts.repository;


import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.ContactSearchDTO;
import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.exception.ConnectionDeniedException;
import com.javalab.contacts.exception.ContactNotFoundException;
import com.javalab.contacts.exception.PersistException;

import java.sql.SQLException;
import java.util.Collection;

public interface ContactRepository {

    ContactShortDTO getContactShortDTO(Integer contactId) throws ConnectionDeniedException, ContactNotFoundException;

    ContactFullDTO getContactFullInfo(Integer id) throws ConnectionDeniedException, ContactNotFoundException;

    Collection<ContactShortDTO> getContactsList(Integer pageNumber) throws ConnectionDeniedException;

    Collection<ContactShortDTO> getByDayAndMonth(Integer day, Integer month) throws ConnectionDeniedException;

    Collection<ContactShortDTO> search(ContactSearchDTO searchObject, int pageNumber) throws ConnectionDeniedException;

    Integer saveContact(ContactFullDTO contact) throws ConnectionDeniedException, PersistException;

    void delete(Integer id) throws ConnectionDeniedException;

    Collection<String> getSexList();

    Collection<String> getMaritalStatusList();

    Collection<String> getPhoneTypeList();

    String getPersonalLink(Integer id) throws ConnectionDeniedException;

    Integer getNumberOfRecordsFound();

    Integer getRowsPePageCount();

    void setRowsPerPageCount(Integer rowsCount);
}
