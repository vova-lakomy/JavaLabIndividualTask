package com.javalab.contacts.service.command;


import com.javalab.contacts.dto.DtoRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListCommand implements Command{

    DtoRepository controller = new DtoRepository();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("contacts",controller.getContactsList());
        request.setAttribute("path","contact-list-form.jsp");
        try {
            request.getRequestDispatcher("/WEB-INF/app.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
