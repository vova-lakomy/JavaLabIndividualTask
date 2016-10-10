package com.javalab.contacts.repository;


import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.ContactSearchDTO;
import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.exception.EntityNotFoundException;
import com.javalab.contacts.exception.EntityPersistException;

import java.util.Collection;

public interface ContactRepository {

    ContactShortDTO getContactShortDTO(Integer contactId) throws ConnectionFailedException, EntityNotFoundException;

    ContactFullDTO getContactFullInfo(Integer id) throws ConnectionFailedException, EntityNotFoundException;

    Collection<ContactShortDTO> getContactsList(Integer pageNumber) throws ConnectionFailedException;

    Collection<ContactShortDTO> getByDayAndMonth(Integer day, Integer month) throws ConnectionFailedException;

    Collection<ContactShortDTO> search(ContactSearchDTO searchObject, int pageNumber) throws ConnectionFailedException;

    Integer saveContact(ContactFullDTO contact) throws ConnectionFailedException, EntityPersistException;

    void delete(Integer id) throws ConnectionFailedException;

    Collection<String> getSexList();

    Collection<String> getMaritalStatusList();

    Collection<String> getPhoneTypeList();

    String getPersonalLink(Integer id) throws ConnectionFailedException;

    Integer getNumberOfRecordsFound();

    Integer getRowsPePageCount();

    void setRowsPerPageCount(Integer rowsCount);
}
