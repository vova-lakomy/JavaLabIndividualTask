package com.javalab.contacts.repository;


import com.javalab.contacts.dao.ContactDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactAttachmentDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcPhoneNumberDao;
import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.dto.PhoneNumberDTO;
import com.javalab.contacts.model.Contact;
import com.javalab.contacts.model.ContactAddress;
import com.javalab.contacts.model.ContactAttachment;
import com.javalab.contacts.model.PhoneNumber;
import com.javalab.contacts.model.enumerations.MartialStatus;
import com.javalab.contacts.model.enumerations.Sex;


import java.time.LocalDate;
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

    public ContactFullDTO getContactFullInfo(Integer id){
        Contact contact = contactDao.get(id);
        Collection<ContactAddress> addresses = contact.getContactAddresses();
        ContactAddress address = null;                 // FIXME: 13.09.16 (takes the last item from collection only)
        LocalDate dateOfBirth = contact.getDateOfBirth();
        for (ContactAddress item : addresses){
            address = item;
        }
        return new ContactFullDTO(id,contact.getFirstName(),contact.getSecondName(), contact.getLastName(),
                dateOfBirth.getDayOfMonth(), dateOfBirth.getMonthValue(), dateOfBirth.getYear(),
                contact.getSex().name().toLowerCase(), contact.getNationality(),
                contact.getMartialStatus().name().toLowerCase(), contact.getWebSite(), contact.geteMail(),
                contact.getCurrentJob(), address.getCountry(), address.getTown(), address.getStreet(),
                address.getHouseNumber(),address.getFlatNumber(), address.getZipCode(),contact.getPhotoLink());
    }

    public void saveContact(ContactFullDTO contact){
        Collection<ContactAddress> addresses = new ArrayList<>();
        contactDao.save(new Contact(
                contact.getId(),
                contact.getFirstName(),
                contact.getSecondName(),
                contact.getLastName(),
                LocalDate.of(contact.getYearOfBirth(),contact.getMonthOfBirth(),contact.getDayOfBirth()),
                Sex.valueOf(contact.getSex().toUpperCase()),
                contact.getNationality(),
                MartialStatus.valueOf(contact.getMartialStatus().toUpperCase()),
                contact.getWebSite(),
                contact.geteMail(),
                contact.getCurrentJob(),
                null,
                null,
                "test",
                null
        ));
    }
}
