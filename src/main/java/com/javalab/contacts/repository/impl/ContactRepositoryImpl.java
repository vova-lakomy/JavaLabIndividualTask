package com.javalab.contacts.repository.impl;


import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.dao.ContactDao;
import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.dao.impl.jdbc.ConnectionManager;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactAttachmentDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcPhoneNumberDao;
import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.ContactSearchDTO;
import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.dto.PhoneNumberDTO;
import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.exception.EntityNotFoundException;
import com.javalab.contacts.exception.EntityPersistException;
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

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ContactRepositoryImpl implements ContactRepository {
    private static final Logger logger = LoggerFactory.getLogger(ContactRepository.class);
    private ContactDao contactDao = new JdbcContactDao();
    private PhoneNumberDao phoneDao = new JdbcPhoneNumberDao();
    private ContactAttachmentDao attachmentDao = new JdbcContactAttachmentDao();

    @Override
    public ContactShortDTO getContactShortDTO(Integer contactId) throws ConnectionFailedException, EntityNotFoundException {
        logger.debug("try to get contact short info by id={}", contactId);
        Contact contactShortInfo = null;
        Connection connection = ConnectionManager.receiveConnection();
        contactDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            logger.debug("transaction opened");
            contactShortInfo = contactDao.get(contactId);
            connection.commit();
            logger.debug("transaction closed");
        } catch (SQLException e) {
            logger.error("",e);
            throw new ConnectionFailedException();
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        ContactShortDTO contactShortDTO = null;
        if (contactShortInfo != null) {
            logger.debug("converting Contact entity to DTO");
            contactShortDTO = createContactShortDTO(contactShortInfo);
        }
        return contactShortDTO;
    }

    @Override
    public ContactFullDTO getContactFullInfo(Integer id) throws ConnectionFailedException, EntityNotFoundException {
        logger.debug("trying to get full contact info by id={}", id);
        Contact contact = null;
        Collection<PhoneNumber> phoneNumbers = null;
        Collection<ContactAttachment> attachments = null;
        Connection connection = ConnectionManager.receiveConnection();
        contactDao.setConnection(connection);
        phoneDao.setConnection(connection);
        attachmentDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            logger.debug("transaction opened");
            contact = contactDao.get(id);
            phoneNumbers = phoneDao.getByContactId(id);
            attachments = attachmentDao.getByContactId(id);
            connection.commit();
            logger.debug("transaction closed");
        } catch (SQLException e) {
            logger.error("", e);
            throw new ConnectionFailedException();
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        ContactFullDTO contactFullDTO = null;
        if (contact != null) {
            logger.debug("got contact instance from db, converting to DTO");
            contact.setAttachments(attachments);
            contact.setPhoneNumbers(phoneNumbers);
            contactFullDTO = createDtoFromContact(contact);
        }
        return contactFullDTO;
    }

    @Override
    public Integer saveContact(ContactFullDTO contactDTO) throws ConnectionFailedException, EntityPersistException {
        logger.debug("trying to save contact {}", contactDTO);
        logger.debug("receiving entities from DTO");
        Contact contact = getContactFromContactDTO(contactDTO);
        Collection<PhoneNumber> phoneNumbers = getPhoneNumbersFromContactDTO(contactDTO);
        Collection<ContactAttachment> attachments = getAttachmentsFromContactDTO(contactDTO);
        Connection connection = ConnectionManager.receiveConnection();
        contactDao.setConnection(connection);
        phoneDao.setConnection(connection);
        attachmentDao.setConnection(connection);
        Integer generatedId = null;
        try {
            connection.setAutoCommit(false);
            logger.debug("transaction opened");
            generatedId = contactDao.save(contact);
            for (PhoneNumber phoneNumber : phoneNumbers){
                phoneDao.save(phoneNumber, generatedId);
            }
            for (ContactAttachment attachment : attachments){
                attachmentDao.save(attachment, generatedId);
            }
            connection.commit();
            logger.debug("transaction closed");
        } catch (SQLException e) {
            logger.error("error while saving contact", e);
            try {
                logger.debug("trying to rollback all changes");
                connection.rollback();
                logger.debug("rollback done");
            } catch (SQLException e1) {
                logger.error("error while rollback transaction \n", e1);
            }
            throw new EntityPersistException("error while saving contact " + contact);
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        logger.debug("returning generated ID = {}", generatedId);
        return generatedId;
    }

    @Override
    public void delete(Integer id) throws ConnectionFailedException {
        logger.debug("trying to delete contact with id={}", id);
        Connection connection = ConnectionManager.receiveConnection();
        contactDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            logger.debug("transaction opened");
            contactDao.delete(id);
            connection.commit();
            logger.debug("transaction closed");
        } catch (SQLException e) {
            logger.error("", e);
            try {
                logger.error("exception while deleting contact, start rollback changes");
                connection.rollback();
                logger.error("rollback done");
            } catch (SQLException e1) {
                logger.error("", e1);
            }
            throw new ConnectionFailedException();
        } finally {
            ConnectionManager.closeConnection(connection);
        }
    }

    @Override
    public Collection<ContactShortDTO> getContactsList(Integer pageNumber) throws ConnectionFailedException {
        logger.debug("trying to get contact list, page number={}", pageNumber);
        Collection<Contact> contactList = null;
        Connection connection = ConnectionManager.receiveConnection();
        contactDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            logger.debug("transaction opened");
            contactList = contactDao.getContactList(pageNumber);
            connection.commit();
            logger.debug("transaction closed");
        } catch (SQLException e) {
            logger.error("", e);
            throw new ConnectionFailedException();
        }
        finally {
            ConnectionManager.closeConnection(connection);
        }
        Collection<ContactShortDTO> contactDTOs = new ArrayList<>();
        if (contactList != null) {
            logger.debug("received list of Contact entities, converting to DTOs");
            for (Contact contact : contactList){
                ContactShortDTO contactShortDTO = createContactShortDTO(contact);
                contactDTOs.add(contactShortDTO);
            }
        }
        return contactDTOs;
    }

    @Override
    public Collection<ContactShortDTO> getByDayAndMonth(Integer day, Integer month) throws ConnectionFailedException {
        logger.debug("receiving Contact entities by day={} and month={} values", day, month);
        Collection<Contact> contactList = null;
        Connection connection = ConnectionManager.receiveConnection();
        contactDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            logger.debug("transaction opened");
            contactList = contactDao.getByDayAndMonth(day, month);
            connection.commit();
            logger.debug("transaction closed");
        } catch (SQLException e) {
            logger.error("", e);
            throw new ConnectionFailedException();
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        Collection<ContactShortDTO> contactDTOs = null;
        if (contactList != null) {
            logger.debug("received list of Contact instances, converting to DTOs");
            contactDTOs = getDTOsFromContactList(contactList);
        }
        logger.debug("returning {}", contactDTOs);
        return contactDTOs;
    }

    @Override
    public Collection<ContactShortDTO> search(ContactSearchDTO searchObject, int pageNumber) throws ConnectionFailedException {
        logger.debug("searching for contact entities. parameters - {}", searchObject);
        Collection<Contact> contactList = null;
        Connection connection = ConnectionManager.receiveConnection();
        contactDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            logger.debug("transaction opened");
            contactList = contactDao.search(searchObject, pageNumber);
            connection.commit();
            logger.debug("transaction closed");
        } catch (SQLException e) {
            logger.error("", e);
            throw new ConnectionFailedException();
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        Collection<ContactShortDTO> contactDTOs = null;
        if (contactList != null) {
            logger.debug("received list of Contact entities, converting to DTOs");
            contactDTOs = getDTOsFromContactList(contactList);
        }
        logger.debug("returning {}", contactDTOs);
        return contactDTOs;
    }

    @Override
    public String getPersonalLink(Integer id) throws ConnectionFailedException {
        logger.debug("retrieving contact attachments directory");
        String personalLink = null;
        Connection connection = ConnectionManager.receiveConnection();
        contactDao.setConnection(connection);
        try {
            connection.setAutoCommit(false);
            logger.debug("transaction opened");
            personalLink = contactDao.getPersonalLink(id);
            connection.commit();
            logger.debug("transaction closed");
        } catch (SQLException e) {
            logger.error("", e);
            throw new ConnectionFailedException();
        } finally {
            ConnectionManager.closeConnection(connection);
        }
        logger.debug("returning {}", personalLink);
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
        logger.debug("converting contact entity to DTO, retrieving fields");
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
        logger.debug("setting fields values to ContactDTO");
        contactShortDTO.setId(id);
        contactShortDTO.setFullName(fullName);
        contactShortDTO.setDateOfBirth(dateOfBirthString);
        contactShortDTO.setAddress(stringAddress);
        contactShortDTO.setCompany(company);
        contactShortDTO.seteMail(eMail);
        return contactShortDTO;
    }

    private Contact getContactFromContactDTO(ContactFullDTO contactDTO) {
        logger.debug("converting DTO to contact entity... retrieving fields");
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
        logger.debug("setting fields values to Contact instance");
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
        logger.debug("creating PhoneNumber entities list from DTO fields");
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
        logger.debug("creating ContactAttachment entities list from DTO fields");
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
        logger.debug("creating ContactAddress entity from DTO fields");
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
            logger.debug("creating PhoneNumberDTOs list from  PhoneNumber entities list");
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
        logger.debug("converting contact entity to DTO... getting fields");
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
        logger.debug("setting fields to contactDTO");
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
        logger.debug("contact DTO ready");
        return contactDTO;
    }

    private Collection<AttachmentDTO> convertAttachmentsToDTO(Collection<ContactAttachment> attachments) {
        logger.debug("converting attachment entities to DTOs");
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

    private Collection<ContactShortDTO> getDTOsFromContactList(Collection<Contact> contactList) {
        logger.debug("converting contact entity list to contact DTO list");
        Collection<ContactShortDTO> contactDTOs = new ArrayList<>();
        for (Contact contact : contactList){
            ContactShortDTO contactShortDTO = createContactShortDTO(contact);
            contactDTOs.add(contactShortDTO);
        }
        return contactDTOs;
    }
}
