package com.javalab.contacts.repository;


import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.ContactSearchDTO;
import com.javalab.contacts.dto.ContactShortDTO;

import java.util.Collection;

public interface ContactDtoRepository {

    ContactShortDTO getContactShortDTO(Integer contactId);

    ContactFullDTO getContactFullInfo(Integer id);

    Collection<ContactShortDTO> getContactsList(Integer pageNumber);

    Collection<ContactShortDTO> getByDayAndMonth(Integer day, Integer month);

    Collection<ContactShortDTO> search(ContactSearchDTO searchObject, int pageNumber);

    Integer saveContact(ContactFullDTO contact);

    void delete(Integer id);

    Collection<String> getSexList();

    Collection<String> getMaritalStatusList();

    Collection<String> getPhoneTypeList();

    String getPersonalLink(Integer id);

    void setPersonalLink(String personalLink, Integer id);

    Integer getNumberOfRecordsFound();

    Integer getRowsPePageCount();

    void setRowsPerPageCount(Integer rowsCount);
}
