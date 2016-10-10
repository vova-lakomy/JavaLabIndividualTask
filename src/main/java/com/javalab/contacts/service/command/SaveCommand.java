package com.javalab.contacts.service.command;

import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.PhoneNumberDTO;
import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.exception.EntityPersistException;
import com.javalab.contacts.repository.ContactRepository;
import com.javalab.contacts.repository.impl.ContactRepositoryImpl;
import com.javalab.contacts.util.UiMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class SaveCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(SaveCommand.class);

    private ContactRepository repository = new ContactRepositoryImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("executing Save Command");
        ContactFullDTO contact = null;
        try {
            logger.debug("defining contact DTO instance");
            contact = getContactDtoFromRequest(request,response);
        } catch (IOException | ServletException e) {
            logger.error("", e);
            UiMessageService.sendAttachmentProcessErrorToUI(request, response);
        }
        Integer returnedId = null;
        try {
            logger.debug("saving defined contact DTO instance");
            returnedId = repository.saveContact(contact);
            logger.debug("contact DTO saved");
        } catch (ConnectionFailedException e) {
            UiMessageService.sendConnectionErrorMessageToUI(request, response);
        } catch (EntityPersistException e) {
            logger.error("",e);
            UiMessageService.sendSaveErrorMessageToUI(request, response);
        }
        UiMessageService.prepareContactSavedPopUpInfoMessage(request);
        String urlRedirectTo = "edit?contactId=" + returnedId;
        request.setAttribute("redirectURL", urlRedirectTo);
        logger.debug("execution of Save command end");
        return "";
    }

    private ContactFullDTO getContactDtoFromRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.debug("creating DTO contact from request... getting fields");
        Integer contactId = null;
        if (isNumeric(request.getParameter("contactId"))) {
            contactId = Integer.parseInt(request.getParameter("contactId"));
        }
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        if (isBlank(firstName) || isBlank(lastName)){
            UiMessageService.sendEmptyFieldErrorToUI(request, response);
        }
        String secondName = request.getParameter("secondName");
        if (isBlank(secondName)) {
            secondName = null;
        }
        String dayOfBirthStr = request.getParameter("dayOfBirth");
        Integer dayOfBirth = null;
        if (isNumeric(dayOfBirthStr)) {
            dayOfBirth = Integer.parseInt(dayOfBirthStr);
        }
        String monthOfBirthStr = request.getParameter("monthOfBirth");
        Integer monthOfBirth= null;
        if(isNumeric(monthOfBirthStr)){
            monthOfBirth = Integer.parseInt(monthOfBirthStr);
        }
        String yearOfBirthStr = request.getParameter("yearOfBirth");
        Integer yearOfBirth = null;
        if(isNumeric(yearOfBirthStr)){
            yearOfBirth = Integer.parseInt(yearOfBirthStr);
        }
        String sex = request.getParameter("sex");
        if (isBlank(sex)){
            sex = null;
        }
        String nationality = request.getParameter("nationality");
        if (isBlank(nationality)){
            nationality = null;
        }
        String maritalStatus = request.getParameter("maritalStatus");
        if (isBlank(maritalStatus)){
            maritalStatus = null;
        }
        String webSite = request.getParameter("webSite");
        if (isBlank(webSite)){
            webSite = null;
        }
        String eMail = request.getParameter("eMail");
        if (isBlank(eMail)){
            eMail = null;
        }
        String currentJob = request.getParameter("currentJob");
        if (isBlank(currentJob)){
            currentJob = null;
        }
        String country = request.getParameter("country");
        if (isBlank(country)){
            country = null;
        }
        String town = request.getParameter("town");
        if (isBlank(town)){
            town = null;
        }
        String street = request.getParameter("street");
        if (isBlank(street)){
            street = null;
        }
        String houseNumberStr = request.getParameter("houseNumber");
        Integer houseNumber = null;
        if (isNumeric(houseNumberStr)){
            houseNumber = Integer.parseInt(houseNumberStr);
        }
        String flatNumberStr = request.getParameter("flatNumber");
        Integer flatNumber = null;
        if (isNumeric(flatNumberStr)){
            flatNumber = Integer.parseInt(flatNumberStr);
        }
        String zipCodeStr = request.getParameter("zipCode");
        Integer zipCode = null;
        if (isNumeric(zipCodeStr)){
            zipCode = Integer.parseInt(zipCodeStr);
        }
        String photoLink = getPhotoLinkFromRequest(request);
        if (isBlank(photoLink)){
            photoLink = null;
        }
        String personalLink = (String) request.getAttribute("personalLink");
        if (isBlank(personalLink)){
            personalLink = null;
        }
        Collection<PhoneNumberDTO> phoneNumbers= getPhoneNumbersFromRequest(request);
        Collection<AttachmentDTO> attachments = getAttachmentsFromRequest(request);
        logger.debug("got all fields for ContactDTO from request... Creating instance...");
        ContactFullDTO contact = new ContactFullDTO();
        contact.setId(contactId);
        contact.setFirstName(firstName);
        contact.setSecondName(secondName);
        contact.setLastName(lastName);
        contact.setDayOfBirth(dayOfBirth);
        contact.setMonthOfBirth(monthOfBirth);
        contact.setYearOfBirth(yearOfBirth);
        contact.setSex(sex);
        contact.setNationality(nationality);
        contact.setMaritalStatus(maritalStatus);
        contact.setWebSite(webSite);
        contact.seteMail(eMail);
        contact.setCurrentJob(currentJob);
        contact.setCountry(country);
        contact.setTown(town);
        contact.setStreet(street);
        contact.setHouseNumber(houseNumber);
        contact.setFlatNumber(flatNumber);
        contact.setZipCode(zipCode);
        contact.setPhotoLink(photoLink);
        contact.setPersonalLink(personalLink);
        contact.setPhoneNumbers(phoneNumbers);
        contact.setAttachments(attachments);
        logger.debug("returning {}", contact);
        return contact;
    }

    private Collection<PhoneNumberDTO> getPhoneNumbersFromRequest(HttpServletRequest request) {
        logger.debug("getting phone numbers fields from request");
        String[] phoneNumberIds = request.getParameterValues("phoneNumberId");
        String[] countryCodes = request.getParameterValues("countryCode");
        String[] operatorCodes = request.getParameterValues("operatorCode");
        String[] numbers = request.getParameterValues("number");
        String[] phoneTypes = request.getParameterValues("phoneType");
        String[] comments = request.getParameterValues("comment");

        Collection<PhoneNumberDTO> phoneNumbers = new ArrayList<>();
        if (phoneNumberIds == null) {
            return phoneNumbers;
        }
        for (int i = 0; i < phoneNumberIds.length; i++) {
            Integer phoneId = null;
            String phoneNumberIdStr = phoneNumberIds[i];
            if (isNumeric(phoneNumberIdStr)) {
                phoneId = Integer.parseInt(phoneNumberIdStr);
            }
            Integer countryCode = null;
            if (isNumeric(countryCodes[i])){
                countryCode = Integer.parseInt(countryCodes[i]);
            }
            Integer operatorCode = null;
            if (isNumeric(operatorCodes[i])){
                operatorCode = Integer.parseInt(operatorCodes[i]);
            }
            Integer number = null;
            if (isNumeric(numbers[i])){
                number = Integer.parseInt(numbers[i]);
            }
            String phoneType = phoneTypes[i];
            String comment = comments[i];
            logger.debug("creating phone number DTO");
            PhoneNumberDTO phoneDTO = new PhoneNumberDTO();
            phoneDTO.setId(phoneId);
            phoneDTO.setCountryCode(countryCode);
            phoneDTO.setOperatorCode(operatorCode);
            phoneDTO.setNumber(number);
            phoneDTO.setType(phoneType);
            phoneDTO.setComment(comment);
            logger.debug("created phone number DTO instance {}, adding to collection", phoneDTO);
            phoneNumbers.add(phoneDTO);
        }
        logger.debug("returning collection of {} phone number DTOs", phoneNumbers.size());
        return phoneNumbers;
    }

    private String getPhotoLinkFromRequest(HttpServletRequest request) {
        if (request.getAttribute("photoLink") == null) {
            return request.getParameter("photoLink");
        } else {
            return (String) request.getAttribute("photoLink");
        }
    }

    private Collection<AttachmentDTO> getAttachmentsFromRequest(HttpServletRequest request) throws IOException, ServletException {
        logger.debug("retrieving attachments info from request");
        List<String> idNames = new ArrayList<>();
        Collection<Part> parts = request.getParts();
        idNames.addAll(parts
                .stream()
                .filter(part -> part.getName().contains("attachmentId"))
                .map(Part::getName)
                .collect(Collectors.toList()));
        logger.debug("found {} files to process", idNames.size());
        Collection<AttachmentDTO> attachmentDTOs = new ArrayList<>();
        idNames.forEach(idName -> {
            String index = idName.substring(idName.lastIndexOf('-'));
            String idNameStr = request.getParameter(idName);
            Integer id = null;
            logger.debug("defining attachment DTO fields");
            if (isNumeric(idNameStr)){
               id = Integer.valueOf(idNameStr);
            }
            String fileName = request.getParameter("attachmentFileName" + index);
            String uploadDateStr = request.getParameter("uploadDate" + index);
            String uploadDate = null;
            if (isNotBlank(uploadDateStr)){
                String[] dateParsed = uploadDateStr.split("\\.");
                    uploadDate = dateParsed[2] + "-" + dateParsed[1] + "-" + dateParsed[0];
                }
            String comment = request.getParameter("attachmentComment" + index);
            String attachmentLink;
            if (request.getAttribute("attachmentLink" + index) != null) {
                attachmentLink = (String) request.getAttribute("attachmentLink" + index);
            } else {
                attachmentLink = request.getParameter("attachmentLink" + index);
            }
            logger.debug("attachment DTO fields defined... creating instance");
            AttachmentDTO attachmentDTO = new AttachmentDTO();
            attachmentDTO.setId(id);
            attachmentDTO.setAttachmentLink(attachmentLink);
            attachmentDTO.setFileName(fileName);
            attachmentDTO.setUploadDate(uploadDate);
            attachmentDTO.setComment(comment);
            logger.debug("created attachment DTO instance {}, adding to collection", attachmentDTO);
            attachmentDTOs.add(attachmentDTO);
        });
        logger.debug("returning collection of {} attachment DTOs", attachmentDTOs.size());
        return attachmentDTOs;
    }
}
