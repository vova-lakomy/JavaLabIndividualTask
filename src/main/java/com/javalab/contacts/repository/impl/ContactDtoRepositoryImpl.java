package com.javalab.contacts.repository.impl;


import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.repository.ContactDtoRepository;

import java.util.Collection;

public class ContactDtoRepositoryImpl implements ContactDtoRepository{
    @Override
    public ContactShortDTO getContactShortDTO(Integer contactId) {
        return null;
    }

    @Override
    public ContactFullDTO getContactFullInfo(Integer id) {
        return null;
    }

    @Override
    public Collection<ContactShortDTO> getContactsList() {
        return null;
    }

    @Override
    public Integer saveContact(ContactFullDTO contact) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Collection<String> getSexList() {
        return null;
    }

    @Override
    public Collection<String> getMartialStatusList() {
        return null;
    }

    @Override
    public Collection<String> getPhoneTypeList() {
        return null;
    }
}
