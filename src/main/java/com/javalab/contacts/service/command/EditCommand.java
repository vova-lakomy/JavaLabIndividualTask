package com.javalab.contacts.service.command;

import com.javalab.contacts.dto.DtoRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditCommand implements Command {

    private DtoRepository controller = new DtoRepository();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Integer contactId = null;
        if (request.getParameter("contactId") != null) {
            contactId = Integer.valueOf(request.getParameter("contactId"));
            request.setAttribute("phoneNumbers",controller.getPhoneNumbers(contactId));
            request.setAttribute("attachments",controller.getAttachments(contactId));

        }


        request.setAttribute("path","contact-edit-form.jsp");
        try {
            request.getRequestDispatcher("/WEB-INF/app.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
