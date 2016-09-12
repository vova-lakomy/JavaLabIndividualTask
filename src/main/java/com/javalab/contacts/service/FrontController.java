package com.javalab.contacts.service;

import com.javalab.contacts.service.command.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FrontController {
    private Map<String,Command> commandMap = new HashMap<>();

    public FrontController(){
        commandMap.put("list",new ListCommand());
        commandMap.put("edit",new EditCommand());
        commandMap.put("search",new SearchCommand());
        commandMap.put("email",new MailCommand());
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        String commandKey = request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/')+1);
        Command command = commandMap.get(commandKey);
        if (command != null) {
            command.execute(request,response);
        } else {
            response.sendRedirect("../404.jsp");
        }
    }

}
