package com.javalab.contacts.repository.impl;


import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.dao.ContactDao;
import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactAttachmentDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcPhoneNumberDao;
import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.ContactSearchDTO;
import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.dto.PhoneNumberDTO;
import com.javalab.contacts.exception.ConnectionDeniedException;
import com.javalab.contacts.exception.ContactNotFoundException;
import com.javalab.contacts.exception.PersistException;
import com.javalab.contacts.model.Contact;
import com.javalab.contacts.model.ContactAddress;
import com.javalab.contacts.model.ContactAttachment;
import com.javalab.contacts.model.PhoneNumber;
import com.javalab.contacts.model.enumerations.MaritalStatus;
import com.javalab.contacts.model.enumerations.PhoneType;
import com.javalab.contacts.model.enumerations.Sex;
import com.javalab.contacts.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.javalab.contacts.dao.impl.jdbc.ConnectionManager.closeConnection;
import static com.javalab.contacts.dao.impl.jdbc.ConnectionManager.receiveConnection;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ContactRepositoryImpl implements ContactRepository {
    private static final Logger logger = LoggerFactory.getLogger(ContactRepository.class);
    private ContactDao contactDao = new JdbcContactDao();
    private PhoneNumberDao phoneDao = new JdbcPhoneNumberDao();
    private ContactAttachmentDao attachmentDao = new JdbcContactAttachmentDao();


    @Override
    public ContactShortDTO getContactShortDTO(Integer contactId) throws ConnectionDeniedException, ContactNotFoundException {
        ContactShortDTO contactShortDTO = null;
        Connection connection = receiveConnection();
        contactDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            Contact contactShortInfo = contactDao.get(contactId);
            connection.commit();
            contactShortDTO = createContactShortDTO(contactShortInfo);
        } catch (SQLException e) {
            logger.error("",e);
        } finally {
            closeConnection(connection);
        }
        return contactShortDTO;
    }

    @Override
    public ContactFullDTO getContactFullInfo(Integer id) throws ConnectionDeniedException, ContactNotFoundException {
        Contact contact = null;
        Connection connection = receiveConnection();
        contactDao.setConnection(connection);
        phoneDao.setConnection(connection);
        attachmentDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            contact = contactDao.get(id);
            Collection<PhoneNumber> phoneNumbers = phoneDao.getByContactId(id);
            Collection<ContactAttachment> attachments = attachmentDao.getByContactId(id);
            connection.commit();
            contact.setAttachments(attachments);
            contact.setPhoneNumbers(phoneNumbers);
        } catch (SQLException e) {
            logger.error("", e);
        } finally {
            closeConnection(connection);
        }
        return createDtoFromContact(contact);
    }

    @Override
    public Integer saveContact(ContactFullDTO contactDTO) throws ConnectionDeniedException, PersistException {
        Contact contact = getContactFromContactDTO(contactDTO);
        Collection<PhoneNumber> phoneNumbers = getPhoneNumbersFromContactDTO(contactDTO);
        Collection<ContactAttachment> attachments = getAttachmentsFromContactDTO(contactDTO);
        Connection connection = receiveConnection();
        contactDao.setConnection(connection);
        phoneDao.setConnection(connection);
        attachmentDao.setConnection(connection);
        Integer generatedId = null;
        try {
            connection.setAutoCommit(false);
            generatedId = contactDao.save(contact);
            for (PhoneNumber phoneNumber : phoneNumbers){
                phoneDao.save(phoneNumber, generatedId);
            }
            for (ContactAttachment attachment : attachments){
                attachmentDao.save(attachment, generatedId);
            }
            connection.commit();
        } catch (SQLException e) {
            logger.error("", e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                logger.error("error while rollback transaction \n{}", e1);
            }
            throw new PersistException("");
        } finally {
            closeConnection(connection);
        }
        return generatedId;
    }

    @Override
    public void delete(Integer id) throws ConnectionDeniedException {
        Connection connection = receiveConnection();
        contactDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            contactDao.delete(id);
            connection.commit();
        } catch (SQLException e) {
            logger.error("", e);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public Collection<ContactShortDTO> getContactsList(Integer pageNumber) throws ConnectionDeniedException {
        Collection<Contact> contactList = null;
        Connection connection = receiveConnection();
        contactDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            contactList = contactDao.getContactList(pageNumber);
            connection.commit();
        } catch (SQLException e) {
            logger.error("", e);
        }
        finally {
            closeConnection(connection);
        }
        Collection<ContactShortDTO> contactDTOs = new ArrayList<>();
        if (contactList != null) {
            for (Contact contact : contactList){
                ContactShortDTO contactShortDTO = createContactShortDTO(contact);
                contactDTOs.add(contactShortDTO);
            }
        }
        return contactDTOs;
    }

    @Override
    public Collection<ContactShortDTO> getByDayAndMonth(Integer day, Integer month) throws ConnectionDeniedException {
        Collection<Contact> contactList = null;
        Connection connection = receiveConnection();
        contactDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            contactList = contactDao.getByDayAndMonth(day, month);
            connection.commit();
        } catch (SQLException e) {
            logger.error("", e);
        } finally {
            closeConnection(connection);
        }
        Collection<ContactShortDTO> contactDTOs = new ArrayList<>();
        if (contactList != null) {
            for (Contact contact : contactList){
                ContactShortDTO contactShortDTO = createContactShortDTO(contact);
                contactDTOs.add(contactShortDTO);
            }
        }
        return contactDTOs;
    }

    @Override
    public Collection<ContactShortDTO> search(ContactSearchDTO searchObject, int pageNumber) throws ConnectionDeniedException {
        Collection<Contact> contactList = null;
        Connection connection = receiveConnection();
        contactDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            contactList = contactDao.search(searchObject, pageNumber);
            connection.commit();
        } catch (SQLException e) {
            logger.error("", e);
        } finally {
            closeConnection(connection);
        }
        Collection<ContactShortDTO> contactDTOs = new ArrayList<>();
        if (contactList != null) {
            for (Contact contact : contactList){
                ContactShortDTO contactShortDTO = createContactShortDTO(contact);
                contactDTOs.add(contactShortDTO);
            }
        }
        return contactDTOs;
    }

    @Override
    public String getPersonalLink(Integer id) throws ConnectionDeniedException {
        String personalLink = null;
        Connection connection = receiveConnection();
        contactDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            personalLink = contactDao.getPersonalLink(id);
            connection.commit();
        } catch (SQLException e) {
            logger.error("", e);
        } finally {
            closeConnection(connection);
        }
        return personalLink;
    }

    @Override
    public Collection<String> getSexList() {
        Collection<String> sexList = new ArrayList<>();
        Arrays.stream(Sex.values()).forEach(value -> sexList.add(value.name().toLowerCase()));
        return sexList;
    }

    @Override
    public Collection<String> getMaritalStatusList() {
        Collection<String> statusList = new ArrayList<>();
        Arrays.stream(MaritalStatus.values()).forEach(value -> statusList.add(value.name().toLowerCase()));
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
    public Integer getRowsPePageCount() {
        return contactDao.getRowsPerPageCount();
    }

    @Override
    public void setRowsPerPageCount(Integer rowsCount) {
        contactDao.setRowsPerPageCount(rowsCount);
    }

    private ContactShortDTO createContactShortDTO(Contact contact) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Integer id = contact.getId();
        String secondName = contact.getSecondName();
        if (secondName == null) {
            secondName = "";
        }
        String fullName = contact.getLastName() + "<br/>"
                + contact.getFirstName() + " " + secondName;

        LocalDate dateOfBirth = contact.getDateOfBirth();
        String dateOfBirthString = "-";
        if (dateOfBirth != null) {
            dateOfBirthString = dateOfBirth.format(formatter);
        }
        ContactAddress contactAddress = contact.getContactAddress();
        String stringAddress = "";
        if (contactAddress != null) {
            String street = contactAddress.getStreet();
            if (street == null) {
                street = "";
            }
            Integer houseNumber = contactAddress.getHouseNumber();
            String houseNumberStr = "";
            if (houseNumber != null) {
                houseNumberStr = String.valueOf(houseNumber);
            }
            Integer flatNumber = contactAddress.getFlatNumber();
            String flatNumberString = "";
            if (flatNumber != null) {
                flatNumberString = String.valueOf(flatNumber);
            }
            String town = contactAddress.getTown();
            if (town == null) {
                town = "";
            }
            String country = contactAddress.getCountry();
            if (country == null) {
                country = "";
            }
            Integer zipCode = contactAddress.getZipCode();
            String zipCodeString = "";
            if (zipCode != null) {
                zipCodeString = String.valueOf(zipCode);
            }
            stringAddress = street + ", "
                    + houseNumberStr + "-"
                    + flatNumberString + "<br/>"
                    + town + ", "
                    + country + "<br/>"
                    + zipCodeString;
            if (stringAddress.equals(", -<br/>, <br/>")) {
                stringAddress = "-";
            }
        }
        String company = contact.getCurrentJob();
        if (company == null) {
            company = "-";
        }
        String eMail = contact.geteMail();
        ContactShortDTO contactShortDTO = new ContactShortDTO();
        contactShortDTO.setId(id);
        contactShortDTO.setFullName(fullName);
        contactShortDTO.setDateOfBirth(dateOfBirthString);
        contactShortDTO.setAddress(stringAddress);
        contactShortDTO.setCompany(company);
        contactShortDTO.seteMail(eMail);
        return contactShortDTO;
    }

    private Contact getContactFromContactDTO(ContactFullDTO contactDTO) {
        Integer id = contactDTO.getId();
        String firstName = contactDTO.getFirstName();
        String secondName = contactDTO.getSecondName();
        String lastName = contactDTO.getLastName();
        Integer yearOfBirth = contactDTO.getYearOfBirth();
        Integer monthOfBirth = contactDTO.getMonthOfBirth();
        Integer dayOfBirth = contactDTO.getDayOfBirth();
        LocalDate dateOfBirth = null;
        if (yearOfBirth != null && monthOfBirth != null && dayOfBirth != null) {
            dateOfBirth = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
        } else if (yearOfBirth != null && monthOfBirth == null && dayOfBirth == null) {
            dateOfBirth = LocalDate.of(yearOfBirth, 1, 1);
        }
        String sexStr = contactDTO.getSex();
        Sex sex = null;
        if (isNotBlank(sexStr)) {
            sex = Sex.valueOf(sexStr.toUpperCase());
        }
        String nationality = contactDTO.getNationality();
        String maritalStatusStr = contactDTO.getMaritalStatus();
        MaritalStatus maritalStatus = null;
        if (isNotBlank(maritalStatusStr)) {
            maritalStatus = MaritalStatus.valueOf(maritalStatusStr.toUpperCase());
        }
        String webSite = contactDTO.getWebSite();
        String eMail = contactDTO.geteMail();
        String currentJob = contactDTO.getCurrentJob();
        ContactAddress address = getAddressFromContactDTO(contactDTO);
        String photoLink = contactDTO.getPhotoLink();
        String personalLink = contactDTO.getPersonalLink();

        Contact contact = new Contact();
        contact.setId(id);
        contact.setFirstName(firstName);
        contact.setSecondName(secondName);
        contact.setLastName(lastName);
        contact.setDateOfBirth(dateOfBirth);
        contact.setSex(sex);
        contact.setNationality(nationality);
        contact.setMaritalStatus(maritalStatus);
        contact.setWebSite(webSite);
        contact.seteMail(eMail);
        contact.setCurrentJob(currentJob);
        contact.setContactAddress(address);
        contact.setPhotoLink(photoLink);
        contact.setPersonalLink(personalLink);
        return contact;
    }


    private Collection<PhoneNumber> getPhoneNumbersFromContactDTO(ContactFullDTO contact) {
        Collection<PhoneNumber> phoneNumbers = new ArrayList<>();
        contact.getPhoneNumbers().forEach(phoneNumber -> {
                    Integer id = phoneNumber.getId();
                    Integer countryCode = phoneNumber.getCountryCode();
                    Integer operatorCode = phoneNumber.getOperatorCode();
                    Integer number = phoneNumber.getNumber();
                    PhoneType phoneType = PhoneType.valueOf(phoneNumber.getType().toUpperCase());
                    String comment = phoneNumber.getComment();
                    PhoneNumber completePhoneNumber = new PhoneNumber();
                    completePhoneNumber.setId(id);
                    completePhoneNumber.setCountryCode(countryCode);
                    completePhoneNumber.setOperatorCode(operatorCode);
                    completePhoneNumber.setPhoneNumber(number);
                    completePhoneNumber.setPhoneType(phoneType);
                    completePhoneNumber.setPhoneComment(comment);
                    phoneNumbers.add(completePhoneNumber);
                }
        );
        return phoneNumbers;
    }

    private Collection<ContactAttachment> getAttachmentsFromContactDTO(ContactFullDTO contact) {
        Collection<ContactAttachment> attachments = new ArrayList<>();
        contact.getAttachments().forEach(attachment -> {
                    Integer id = attachment.getId();
                    String attachmentName = attachment.getFileName();
                    String attachmentLink = attachment.getAttachmentLink();
                    String comment = attachment.getComment();
                    LocalDate dateOfUpload = null;
                    if (attachment.getUploadDate() != null) {
                        dateOfUpload = LocalDate.parse(attachment.getUploadDate());
                    }
                    ContactAttachment contactAttachment = new ContactAttachment();
                    contactAttachment.setId(id);
                    contactAttachment.setAttachmentName(attachmentName);
                    contactAttachment.setAttachmentLink(attachmentLink);
                    contactAttachment.setAttachmentComment(comment);
                    contactAttachment.setDateOfUpload(dateOfUpload);
                    attachments.add(contactAttachment);
                }
        );
        return attachments;
    }

    private ContactAddress getAddressFromContactDTO(ContactFullDTO contact) {
        Integer id = contact.getId();
        String country = contact.getCountry();
        String town = contact.getTown();
        String street = contact.getStreet();
        Integer houseNumber = contact.getHouseNumber();
        Integer flatNumber = contact.getFlatNumber();
        Integer zipCode = contact.getZipCode();
        ContactAddress address = new ContactAddress();
        address.setId(id);
        address.setCountry(country);
        address.setTown(town);
        address.setStreet(street);
        address.setHouseNumber(houseNumber);
        address.setFlatNumber(flatNumber);
        address.setZipCode(zipCode);
        return address;
    }

    private Collection<PhoneNumberDTO> convertPhoneNumbersToDTO(Collection<PhoneNumber> phoneNumbers) {
        if (phoneNumbers == null) {
            return null;
        } else {
            Collection<PhoneNumberDTO> phoneNumberDTOs = new ArrayList<>();
            phoneNumbers.forEach(phone -> {
                Integer id = phone.getId();
                Integer countryCode = phone.getCountryCode();
                Integer operatorCode = phone.getOperatorCode();
                Integer number = phone.getPhoneNumber();
                String fullNumber = "+"
                        + countryCode + " ("
                        + operatorCode + ") "
                        + number;
                String phoneType = phone.getPhoneType().name().toLowerCase();
                String comment = phone.getPhoneComment();

                PhoneNumberDTO phoneDTO = new PhoneNumberDTO();
                phoneDTO.setId(id);
                phoneDTO.setCountryCode(countryCode);
                phoneDTO.setOperatorCode(operatorCode);
                phoneDTO.setNumber(number);
                phoneDTO.setFullNumber(fullNumber);
                phoneDTO.setType(phoneType);
                phoneDTO.setComment(comment);
                phoneNumberDTOs.add(phoneDTO);
            });
            if (phoneNumberDTOs.size() > 0) {
                return phoneNumberDTOs;
            } else return null;
        }
    }

    private ContactFullDTO createDtoFromContact(Contact contact) {
        ContactAddress address = contact.getContactAddress();
        LocalDate dateOfBirth = contact.getDateOfBirth();
        String firstName = contact.getFirstName();
        String secondName = contact.getSecondName();
        String lastName = contact.getLastName();
        Integer day = null;
        Integer month = null;
        Integer year = null;
        if (dateOfBirth != null) {
            day = dateOfBirth.getDayOfMonth();
            month = dateOfBirth.getMonthValue();
            year = dateOfBirth.getYear();
        }
        Sex sex = contact.getSex();
        String sexString = null;
        if (sex != null) {
            sexString = sex.name().toLowerCase();
        }
        String nationality = contact.getNationality();
        MaritalStatus maritalStatus = contact.getMaritalStatus();
        String maritalStatusString = null;
        if (maritalStatus != null) {
            maritalStatusString = maritalStatus.name().toLowerCase();
        }
        String webSite = contact.getWebSite();
        String eMail = contact.geteMail();
        String currentJob = contact.getCurrentJob();
        String photoLink = contact.getPhotoLink();
        String country = address.getCountry();
        String town = address.getTown();
        Integer zipCode = address.getZipCode();
        String street = address.getStreet();
        Integer houseNumber = address.getHouseNumber();
        Integer flatNumber = address.getFlatNumber();
        Collection<PhoneNumberDTO> phoneNumbers = convertPhoneNumbersToDTO(contact.getPhoneNumbers());
        Collection<AttachmentDTO> attachments = convertAttachmentsToDTO(contact.getAttachments());

        ContactFullDTO contactDTO = new ContactFullDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setFirstName(firstName);
        contactDTO.setSecondName(secondName);
        contactDTO.setLastName(lastName);
        contactDTO.setDayOfBirth(day);
        contactDTO.setMonthOfBirth(month);
        contactDTO.setYearOfBirth(year);
        contactDTO.setSex(sexString);
        contactDTO.setNationality(nationality);
        contactDTO.setMaritalStatus(maritalStatusString);
        contactDTO.setWebSite(webSite);
        contactDTO.seteMail(eMail);
        contactDTO.setCurrentJob(currentJob);
        contactDTO.setCountry(country);
        contactDTO.setTown(town);
        contactDTO.setStreet(street);
        contactDTO.setHouseNumber(houseNumber);
        contactDTO.setFlatNumber(flatNumber);
        contactDTO.setZipCode(zipCode);
        contactDTO.setPhotoLink(photoLink);
        contactDTO.setPhoneNumbers(phoneNumbers);
        contactDTO.setAttachments(attachments);
        return contactDTO;
    }

    private Collection<AttachmentDTO> convertAttachmentsToDTO(Collection<ContactAttachment> attachments) {
        Collection<AttachmentDTO> attachmentDTOs = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        attachments.forEach(attachment -> {
            Integer id = attachment.getId();
            String attachmentLink = attachment.getAttachmentLink();
            String fileName;
            if (attachmentLink != null) {
                fileName = attachmentLink.substring(attachmentLink.lastIndexOf(File.separator) + 1);
            } else fileName = "no-name";

            String uploadDate = null;
            LocalDate dateOfUpload = attachment.getDateOfUpload();
            if (dateOfUpload != null) {
                uploadDate = dateOfUpload.format(formatter);
            }
            String comment = attachment.getAttachmentComment();

            AttachmentDTO attachmentDTO = new AttachmentDTO();
            attachmentDTO.setId(id);
            attachmentDTO.setAttachmentLink(attachmentLink);
            attachmentDTO.setFileName(fileName);
            attachmentDTO.setUploadDate(uploadDate);
            attachmentDTO.setComment(comment);
            attachmentDTOs.add(attachmentDTO);
        });
        return attachmentDTOs;
    }
}
