package com.javalab.contacts.service.command;

import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.repository.DtoRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveCommand implements Command {

    DtoRepository repository = new DtoRepository();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        Integer id = null;
        if (!request.getParameter("contactId").equals("")){
            id = Integer.parseInt(request.getParameter("contactId"));
        }

        repository.saveContact(
                new ContactFullDTO(
                        id,
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
                        request.getParameter("photoLink"),
                        null,null)
        );

        request.setAttribute("path","contact-list-form.jsp");
        try {
            response.sendRedirect("list");
//            request.getRequestDispatcher("/WEB-INF/app.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
