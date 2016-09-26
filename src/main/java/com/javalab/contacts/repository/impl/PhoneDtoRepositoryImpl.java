package com.javalab.contacts.repository.impl;

import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcPhoneNumberDao;
import com.javalab.contacts.dto.PhoneNumberDTO;
import com.javalab.contacts.model.PhoneNumber;
import com.javalab.contacts.repository.PhoneDtoRepository;

import java.util.Collection;


public class PhoneDtoRepositoryImpl implements PhoneDtoRepository {
    private PhoneNumberDao numberDao = new JdbcPhoneNumberDao();

    @Override
    public Collection<PhoneNumberDTO> getPhoneNumberDTOs(Collection<PhoneNumber> phoneNumbers) {
        return null;
    }

    @Override
    public void delete(Integer id){
        numberDao.delete(id);
    }
}
