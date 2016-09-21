package com.javalab.contacts.repository;


import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.ContactShortDTO;

import java.util.Collection;

public interface ContactDtoRepository {

    ContactShortDTO getContactShortDTO(Integer contactId);

    ContactFullDTO getContactFullInfo(Integer id);

    Collection<ContactShortDTO> getContactsList(Integer pageNumber);

    Integer saveContact(ContactFullDTO contact);

    void delete(Integer id);

    Collection<String> getSexList();

    Collection<String> getMartialStatusList();

    Collection<String> getPhoneTypeList();
}
