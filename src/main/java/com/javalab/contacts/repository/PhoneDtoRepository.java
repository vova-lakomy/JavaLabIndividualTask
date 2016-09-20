package com.javalab.contacts.repository;


import com.javalab.contacts.dto.PhoneNumberDTO;
import com.javalab.contacts.model.PhoneNumber;

import java.util.Collection;

public interface PhoneDtoRepository {

    Collection<PhoneNumberDTO> getPhoneNumberDTOs(Collection<PhoneNumber> phoneNumbers);
}
