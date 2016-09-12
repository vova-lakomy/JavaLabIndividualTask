package com.javalab.contacts.service.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {


        request.setAttribute("path","contact-search-form.jsp");
        try {
            request.getRequestDispatcher("/WEB-INF/app.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
