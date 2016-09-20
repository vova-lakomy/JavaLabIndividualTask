package com.javalab.contacts.repository;


import com.javalab.contacts.dao.ContactDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactAttachmentDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactDao;
import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.dto.PhoneNumberDTO;
import com.javalab.contacts.model.Contact;
import com.javalab.contacts.model.ContactAddress;
import com.javalab.contacts.model.ContactAttachment;
import com.javalab.contacts.model.PhoneNumber;
import com.javalab.contacts.model.enumerations.MartialStatus;
import com.javalab.contacts.model.enumerations.PhoneType;
import com.javalab.contacts.model.enumerations.Sex;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class DtoRepository {
    private ContactDao contactDao = new JdbcContactDao();

    public Collection<String> getSexList() {
        Collection<String> sexList = new ArrayList<>();
        Arrays.stream(Sex.values()).forEach(value -> sexList.add(value.name().toLowerCase()));
        return sexList;
    }

    public Collection<String> getMartialStatusList() {
        Collection<String> statusList = new ArrayList<>();
        Arrays.stream(MartialStatus.values()).forEach(value -> statusList.add(value.name().toLowerCase()));
        return statusList;
    }

    public Collection<String> getPhoneTypeList() {
        Collection<String> typesList = new ArrayList<>();
        Arrays.stream(PhoneType.values()).forEach(value -> typesList.add(value.name().toLowerCase()));
        return typesList;
    }

    public ContactShortDTO getContactShortDTO(Integer contactId) {
        Contact contactShortInfo = contactDao.getContactShortInfo(contactId);
        return createContactShortDTO(contactShortInfo);
    }

    public Collection<ContactShortDTO> getContactsList() {
        Collection<ContactShortDTO> contactDTOs = new ArrayList<>();
        contactDao.getContactList().forEach(contact ->
                contactDTOs.add(createContactShortDTO(contact)));
        return contactDTOs;
    }

    public Collection<PhoneNumberDTO> getPhoneNumberDTOs(Collection<PhoneNumber> phoneNumbers) {
        if (phoneNumbers != null) {
            Collection<PhoneNumberDTO> phoneNumberDTOs = new ArrayList<>();
            phoneNumbers.forEach(phone -> {
                Integer id = phone.getId();
                int countryCode = phone.getCountryCode();
                int operatorCode = phone.getOperatorCode();
                int number = phone.getPhoneNumber();
                String fullNumber = "+"
                        + countryCode + " ("
                        + operatorCode + ") "
                        + number;
                String phoneType = phone.getPhoneType().name().toLowerCase();
                String comment = phone.getPhoneComment();

                phoneNumberDTOs.add(new PhoneNumberDTO(id, countryCode, operatorCode, number, phoneType, comment, fullNumber));
            });
            return phoneNumberDTOs.size() > 0 ? phoneNumberDTOs : null;
        } else {
            return null;
        }
    }

    public Collection<AttachmentDTO> getAttachments(Integer contactId) {
        if (contactId != null) {
            Collection<AttachmentDTO> attachmentDTOs = new ArrayList<>();
            new JdbcContactAttachmentDao().getByContactId(contactId).forEach(attachment -> {
                Integer id = attachment.getId();
                String fileName = attachment.getAttachmentLink()
                        .substring(attachment.getAttachmentLink().lastIndexOf("/") + 1);
                String uploadDate = attachment.getDateOfUpload().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                String comment = attachment.getAttachmentComment();
                String attachmentLink = attachment.getAttachmentLink();

                attachmentDTOs.add(new AttachmentDTO(id, fileName, uploadDate, comment, attachmentLink));
            });
            return attachmentDTOs.size() > 0 ? attachmentDTOs : null;
        } else {
            return null;
        }
    }

    public ContactFullDTO getContactFullInfo(Integer id) {
        Contact contact = contactDao.get(id);
        ContactAddress address = contact.getContactAddress();
        LocalDate dateOfBirth = contact.getDateOfBirth();

        return new ContactFullDTO(id,
                contact.getFirstName(),
                contact.getSecondName(),
                contact.getLastName(),
                dateOfBirth.getDayOfMonth(),
                dateOfBirth.getMonthValue(),
                dateOfBirth.getYear(),
                contact.getSex().name().toLowerCase(),
                contact.getNationality(),
                contact.getMartialStatus().name().toLowerCase(),
                contact.getWebSite(),
                contact.geteMail(),
                contact.getCurrentJob(),
                address.getCountry(),
                address.getTown(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getFlatNumber(),
                address.getZipCode(),
                contact.getPhotoLink(),
                getPhoneNumberDTOs(contact.getPhoneNumbers()),
                null);
    }

    public void saveContact(ContactFullDTO contact) {
        Collection<PhoneNumber> phoneNumbers = new ArrayList<>();
        contact.getPhoneNumbers().forEach(phoneN -> {
            phoneNumbers.add(new PhoneNumber(phoneN.getId(), phoneN.getCountryCode(), phoneN.getOperatorCode(),
                    phoneN.getNumber(), PhoneType.valueOf(phoneN.getType().toUpperCase()), phoneN.getComment()));
        });
        Collection<ContactAttachment> attachments = new ArrayList<>();
        contact.getAttachments().forEach(attachment -> {
            attachments.add(new ContactAttachment(attachment.getId(),attachment.getAttachmentLink(),
                    attachment.getComment(), LocalDate.parse(attachment.getUploadDate())));
        });
        ContactAddress address =
                new ContactAddress(contact.getId(),
                        contact.getCountry(),
                        contact.getTown(),
                        contact.getStreet(),
                        contact.getHouseNumber(),
                        contact.getFlatNumber(),
                        contact.getZipCode());
        contactDao.save(new Contact(
                contact.getId(),
                contact.getFirstName(),
                contact.getSecondName(),
                contact.getLastName(),
                LocalDate.of(contact.getYearOfBirth(), contact.getMonthOfBirth(), contact.getDayOfBirth()),
                Sex.valueOf(contact.getSex().toUpperCase()),
                contact.getNationality(),
                MartialStatus.valueOf(contact.getMartialStatus().toUpperCase()),
                contact.getWebSite(),
                contact.geteMail(),
                contact.getCurrentJob(),
                address,
                attachments,
                contact.getPhotoLink(),
                phoneNumbers
        ));
    }

    private ContactShortDTO createContactShortDTO(Contact contact) {

        Integer id = contact.getId();

        String fullName = contact.getLastName() + "<br/>"
                + contact.getFirstName() + " "
                + contact.getSecondName();

        String dateOfBirth = contact.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        ContactAddress contactAddress = contact.getContactAddress();
        String stringAddress =
                contactAddress.getStreet() + ", "
                        + contactAddress.getHouseNumber() + "-"
                        + contactAddress.getFlatNumber() + "<br/>"
                        + contactAddress.getTown() + ", "
                        + contactAddress.getCountry() + "<br/>"
                        + contactAddress.getZipCode();

        String company = contact.getCurrentJob();

        String eMail = contact.geteMail();

        return new ContactShortDTO(id, fullName, dateOfBirth, stringAddress, company, eMail);
    }
}
