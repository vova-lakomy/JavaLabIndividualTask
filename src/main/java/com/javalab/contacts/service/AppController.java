package com.javalab.contacts.service;

import com.javalab.contacts.service.command.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class AppController {
    private Map<String,Command> commandMap = new HashMap<>();

    public AppController(){
        commandMap.put("list",new ListCommand());               // TODO: 13.09.16  make lazy initialization
        commandMap.put("edit",new EditCommand());
        commandMap.put("search",new SearchCommand());
        commandMap.put("email",new MailCommand());
        commandMap.put("save",new SaveCommand());
        commandMap.put("delete",new DeleteCommand());
        commandMap.put("uploadPhoto", new UploadPhotoCommand());
    }



    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<String> commandKeys = new ArrayList<>();
        String[] optionalCommands = request.getParameterValues("optionalCommand");
        if (optionalCommands != null) {
            for (String s : optionalCommands) {
                commandKeys.add(s);
            }
        }
        commandKeys.add(request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/')+1));

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        for (String key : commandKeys){
            Command command = commandMap.get(key);
            if (command != null) {
                command.execute(request,response);
            }else {
                response.sendRedirect("../404.jsp");
            }
        }
    }

}
