package com.javalab.contacts.service.command;


import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.repository.ContactDtoRepository;
import com.javalab.contacts.repository.impl.ContactDtoRepositoryImpl;
import com.javalab.contacts.util.MailSender;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MailCommand implements Command {

    private MailSender mailSender = new MailSender();
    private ContactDtoRepository repository = new ContactDtoRepositoryImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {
            Collection<ContactShortDTO> contactShortDTOs = new ArrayList<>();
            Arrays.stream(selectedIds).forEach(id ->
                    contactShortDTOs.add(repository.getContactShortDTO(Integer.valueOf(id))));
            request.setAttribute("emailContacts", contactShortDTOs);
            request.setAttribute("path", "contact-email-form.jsp");
        } else if (request.getParameterValues("mailTo") != null) {
            sendMail(request);
            try {
                response.sendRedirect("list");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            request.setAttribute("path", "contact-email-form.jsp");
        }

        try {
            request.getRequestDispatcher("/WEB-INF/app.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMail(HttpServletRequest request) {
        String mailSubject = request.getParameter("mailSubject");
        if (mailSubject == null) {
            mailSubject = "(No subject)";
        }
        String mailText = request.getParameter("mailText");
        if (mailText == null) {
            mailText = "";
        }
        mailSender.sendMail(getAddressesFromRequest(request), mailSubject, mailText);
    }

    private Address[] getAddressesFromRequest(HttpServletRequest request) {
        String[] mailTo = request.getParameterValues("mailTo");
        Address[] addresses = new Address[mailTo.length];
        for (int i = 0; i < mailTo.length; i++) {
            try {
                addresses[i] = new InternetAddress(mailTo[i]);
            } catch (AddressException e) {
                e.printStackTrace();
            }

        }
        return addresses;
    }
}
