package com.javalab.contacts.dto;


import com.javalab.contacts.dao.ContactDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactAttachmentDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcPhoneNumberDao;
import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.dto.PhoneNumberDTO;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;


public class DtoRepository {
    private ContactDao contactDao = new JdbcContactDao();

    public Collection<ContactShortDTO> getContactsList(){

        Collection<ContactShortDTO> contactDTOs = new ArrayList<>();
        contactDao.getAllContacts().forEach(contact -> {

            Integer id = contact.getId();

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

            contactDTOs.add(new ContactShortDTO(id,fullName,dateOfBirth,addresses,company));
        });

        return contactDTOs;
    }

    public Collection<PhoneNumberDTO> getPhoneNumbers (Integer contactId){
        if (contactId != null) {
            Collection<PhoneNumberDTO> phoneNumberDTOs = new ArrayList<>();
            new JdbcPhoneNumberDao().getByContactId(contactId).forEach(phone -> {
                Integer id = phone.getId();
                String phoneNumber = "+"
                        + phone.getCountryCode() + " ("
                        + phone.getOperatorCode() + ") "
                        + phone.getPhoneNumber();
                String phoneType = phone.getPhoneType().name().toLowerCase();
                String comment = phone.getPhoneComment();

                phoneNumberDTOs.add(new PhoneNumberDTO(id,phoneNumber,phoneType,comment));
            });
            return phoneNumberDTOs.size() > 0 ? phoneNumberDTOs : null;
        } else {
            return null;
        }
    }

    public Collection<AttachmentDTO> getAttachments (Integer contactId){
        if (contactId != null) {
            Collection<AttachmentDTO> attachmentDTOs = new ArrayList<>();
            new JdbcContactAttachmentDao().getByContactId(contactId).forEach(attachment -> {
                Integer id = attachment.getId();
                String fileName = attachment.getAttachmentLink()
                        .substring(attachment.getAttachmentLink().lastIndexOf("/")+1);
                String uploadDate = attachment.getDateOfUpload().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                String comment = attachment.getAttachmentComment();

                attachmentDTOs.add(new AttachmentDTO(id,fileName,uploadDate,comment));
            });
            return attachmentDTOs.size() > 0 ? attachmentDTOs : null;
        } else {
            return null;
        }
    }
}
