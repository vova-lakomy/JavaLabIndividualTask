package com.javalab.contacts.service.command;

import com.javalab.contacts.dao.ContactAddressDao;
import com.javalab.contacts.dto.AddressDTO;
import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.dto.PhoneNumberDTO;
import com.javalab.contacts.repository.DtoRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;

public class SaveCommand implements Command {

    private DtoRepository repository = new DtoRepository();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Integer contactId = null;
        if (!request.getParameter("contactId").equals("")){
            contactId = Integer.parseInt(request.getParameter("contactId"));
        }
        String photoLink;
        if (request.getAttribute("photoLink") == null){
            photoLink = request.getParameter("photoLink");
        } else {
            photoLink = (String) request.getAttribute("photoLink");
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
                        photoLink,
                        createPhoneNumbersFromRequest(request),
                        null)
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
}
