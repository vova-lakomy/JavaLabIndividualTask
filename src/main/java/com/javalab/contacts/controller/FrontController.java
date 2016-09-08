package com.javalab.contacts.controller;


import com.javalab.contacts.dao.ContactDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactDao;
import com.javalab.contacts.dto.ContactDTO;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;


public class FrontController {
    private ContactDao contactDao = new JdbcContactDao();

    public Collection<ContactDTO> getAllContacts(){

        Collection<ContactDTO> contactDTOs = new ArrayList<>();
        contactDao.getAllContacts().forEach(contact -> {

            String fullName = contact.getLastName() + "<br/>"
                            + contact.getFirstName() + " "
                            + contact.getSecondName();

            String dateOfBirth = contact.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            Collection<String> addresses = new ArrayList<>();
            contact.getContactAddresses().forEach(address ->
                    addresses.add(
                          address.getStreet() + ", "
                        + address.getHouseNumber() + "-"
                        + address.getFlatNumber() + "<br/>"
                        + address.getTown() + ", "
                        + address.getCountry() + "<br/>"
                        + address.getZipCode())
            );

            String company = contact.getCurrentJob();

            contactDTOs.add(new ContactDTO(fullName,dateOfBirth,addresses,company));
        });

        return contactDTOs;
    }
}
