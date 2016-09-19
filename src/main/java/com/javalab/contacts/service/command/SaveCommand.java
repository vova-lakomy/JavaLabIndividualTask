package com.javalab.contacts.service.command;

import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.PhoneNumberDTO;
import com.javalab.contacts.repository.DtoRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SaveCommand implements Command {

    private DtoRepository repository = new DtoRepository();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        Integer contactId = null;
        if (!request.getParameter("contactId").equals("")){
            contactId = Integer.parseInt(request.getParameter("contactId"));
        }

        repository.saveContact(
                new ContactFullDTO(
                        contactId,
                        request.getParameter("firstName"),
                        request.getParameter("secondName"),
                        request.getParameter("lastName"),
                        Integer.parseInt(request.getParameter("dayOfBirth")),
                        Integer.parseInt(request.getParameter("monthOfBirth")),
                        Integer.parseInt(request.getParameter("yearOfBirth")),
                        request.getParameter("sex"),
                        request.getParameter("nationality"),
                        request.getParameter("martialStatus"),
                        request.getParameter("webSite"),
                        request.getParameter("eMail"),
                        request.getParameter("currentJob"),
                        request.getParameter("country"),
                        request.getParameter("town"),
                        request.getParameter("street"),
                        Integer.parseInt(request.getParameter("houseNumber")),
                        Integer.parseInt(request.getParameter("flatNumber")),
                        Integer.parseInt(request.getParameter("zipCode")),
                        definePhotoLink(request),
                        createPhoneNumbersFromRequest(request),
                        defineAttachments(request))
        );

        request.setAttribute("path","contact-list-form.jsp");
        try {
            response.sendRedirect("list");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Collection<PhoneNumberDTO> createPhoneNumbersFromRequest(HttpServletRequest request){
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
            Integer phoneId;
            if (!phoneNumberIds[i].equals("")) {
                phoneId = Integer.parseInt(phoneNumberIds[i]);
            } else {
                phoneId = null;
            }

            phoneNumbers.add(new PhoneNumberDTO(phoneId, Integer.parseInt(countryCodes[i]),
                    Integer.parseInt(operatorCodes[i]), Integer.parseInt(numbers[i]), phoneTypes[i], comments[i]));
        }
        return phoneNumbers;
    }

    private String definePhotoLink(HttpServletRequest request){
        if (request.getAttribute("photoLink") == null){
            return request.getParameter("photoLink").equals("") ? null : request.getParameter("photoLink");
        } else {
            return  (String) request.getAttribute("photoLink");
        }
    }

    private Collection<AttachmentDTO> defineAttachments(HttpServletRequest request){
        List<String> idNames = new ArrayList<>();
        try {
            request.getParts().forEach(part -> {
                if (part.getName().contains("attachmentId")){
                    idNames.add(part.getName());
                }
            });
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        Collection<AttachmentDTO> attachmentDTOs = new ArrayList<>();
        idNames.forEach(idName -> {
            String index = idName.substring(idName.lastIndexOf('-'));
//            public AttachmentDTO(Integer id, String fileName, String uploadDate, String comment, String attachmentLink)
            Integer id = (request.getParameter(idName) == null)? null : Integer.valueOf(request.getParameter(idName));
            String fileName = request.getParameter("attachmentFileName"+index);
            String uploadDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String comment = request.getParameter("attachmentComment"+index);
            String attachmentLink;
            if (request.getAttribute("attachmentLink"+index) != null){
                attachmentLink = (String) request.getAttribute("attachmentLink"+index);
            } else {
                attachmentLink = request.getParameter("attachmentLink"+index);
            }
            attachmentDTOs.add(new AttachmentDTO(id,fileName,uploadDate,comment,attachmentLink));
        });
        System.out.println();
        return attachmentDTOs;
    }
}
