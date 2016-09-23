package com.javalab.contacts.repository.impl;


import com.javalab.contacts.dao.ContactDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactDao;
import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.ContactSearchDTO;
import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.dto.PhoneNumberDTO;
import com.javalab.contacts.model.Contact;
import com.javalab.contacts.model.ContactAddress;
import com.javalab.contacts.model.ContactAttachment;
import com.javalab.contacts.model.PhoneNumber;
import com.javalab.contacts.model.enumerations.MartialStatus;
import com.javalab.contacts.model.enumerations.PhoneType;
import com.javalab.contacts.model.enumerations.Sex;
import com.javalab.contacts.repository.ContactDtoRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ContactDtoRepositoryImpl implements ContactDtoRepository {
    private ContactDao contactDao = new JdbcContactDao();


    @Override
    public ContactShortDTO getContactShortDTO(Integer contactId) {
        Contact contactShortInfo = contactDao.getContactShortInfo(contactId);
        return createContactShortDTO(contactShortInfo);
    }

    @Override
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
                definePhoneNumberDTOs(contact.getPhoneNumbers()),
                null);
    }

    @Override
    public Collection<ContactShortDTO> getContactsList(Integer pageNumber) {
        Collection<ContactShortDTO> contactDTOs = new ArrayList<>();
        contactDao.getContactList(pageNumber).forEach(contact ->
                contactDTOs.add(createContactShortDTO(contact)));
        return contactDTOs;
    }

    @Override
    public Collection<ContactShortDTO> search(ContactSearchDTO searchObject, int pageNumber) {
        Collection<ContactShortDTO> contactDTOs = new ArrayList<>();
        contactDao.search(searchObject, pageNumber).forEach(contact ->
                contactDTOs.add(createContactShortDTO(contact)));
        return contactDTOs;
    }

    @Override
    public Integer saveContact(ContactFullDTO contact) {
        Collection<PhoneNumber> phoneNumbers = definePhoneNumbers(contact);
        Collection<ContactAttachment> attachments = defineContactAttachments(contact);
        ContactAddress address = defineContactsAddress(contact);

        return contactDao.save(new Contact(
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
                phoneNumbers)
        );
    }

    @Override
    public void delete(Integer id) {
        contactDao.delete(id);
    }

    @Override
    public Collection<String> getSexList() {
        Collection<String> sexList = new ArrayList<>();
        Arrays.stream(Sex.values()).forEach(value -> sexList.add(value.name().toLowerCase()));
        return sexList;
    }

    @Override
    public Collection<String> getMartialStatusList() {
        Collection<String> statusList = new ArrayList<>();
        Arrays.stream(MartialStatus.values()).forEach(value -> statusList.add(value.name().toLowerCase()));
        return statusList;
    }

    @Override
    public Collection<String> getPhoneTypeList() {
        Collection<String> typesList = new ArrayList<>();
        Arrays.stream(PhoneType.values()).forEach(value -> typesList.add(value.name().toLowerCase()));
        return typesList;
    }

    @Override
    public Integer getNumberOfRecordsFound() {
        return contactDao.getNumberOfRecordsFound();
    }

    @Override
    public Integer getRowsPePageCount(){
        return contactDao.getRowsPerPageCount();
    }

    @Override
    public void setRowsPePageCount(Integer rowsCount){
        contactDao.setRowsPerPageCount(rowsCount);
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

    private Collection<PhoneNumber> definePhoneNumbers(ContactFullDTO contact) {
        Collection<PhoneNumber> phoneNumbers = new ArrayList<>();
        contact.getPhoneNumbers().forEach(phoneNumber ->
                phoneNumbers.add(new PhoneNumber(phoneNumber.getId(),
                        phoneNumber.getCountryCode(),
                        phoneNumber.getOperatorCode(),
                        phoneNumber.getNumber(),
                        PhoneType.valueOf(phoneNumber.getType().toUpperCase()),
                        phoneNumber.getComment()))
        );
        return phoneNumbers;
    }

    private Collection<ContactAttachment> defineContactAttachments(ContactFullDTO contact) {
        Collection<ContactAttachment> attachments = new ArrayList<>();
        contact.getAttachments().forEach(attachment ->
                attachments.add(new ContactAttachment(attachment.getId(),
                        attachment.getAttachmentLink(),
                        attachment.getComment(),
                        LocalDate.parse(attachment.getUploadDate())))
        );
        return attachments;
    }

    private ContactAddress defineContactsAddress(ContactFullDTO contact) {
        return new ContactAddress(contact.getId(),
                contact.getCountry(),
                contact.getTown(),
                contact.getStreet(),
                contact.getHouseNumber(),
                contact.getFlatNumber(),
                contact.getZipCode());
    }

    private Collection<PhoneNumberDTO> definePhoneNumberDTOs(Collection<PhoneNumber> phoneNumbers) {
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
}
