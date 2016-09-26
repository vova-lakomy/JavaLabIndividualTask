package com.javalab.contacts.service;

import com.javalab.contacts.service.command.Command;
import com.javalab.contacts.service.command.DeleteAttachmentCommand;
import com.javalab.contacts.service.command.DeleteContactCommand;
import com.javalab.contacts.service.command.DeletePhoneCommand;
import com.javalab.contacts.service.command.EditCommand;
import com.javalab.contacts.service.command.ListCommand;
import com.javalab.contacts.service.command.MailCommand;
import com.javalab.contacts.service.command.RenameAttachmentCommand;
import com.javalab.contacts.service.command.SaveCommand;
import com.javalab.contacts.service.command.SearchCommand;
import com.javalab.contacts.service.command.UploadAttachmentCommand;
import com.javalab.contacts.service.command.UploadPhotoCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    private Map<String,Command> commandMap = new HashMap<>();

    public AppController(){
        commandMap.put("list",new ListCommand());               // TODO: 13.09.16  make lazy initialization
        commandMap.put("edit",new EditCommand());
        commandMap.put("search",new SearchCommand());
        commandMap.put("mail",new MailCommand());
        commandMap.put("save",new SaveCommand());
        commandMap.put("deleteContact",new DeleteContactCommand());
        commandMap.put("deletePhone",new DeletePhoneCommand());
        commandMap.put("uploadPhoto", new UploadPhotoCommand());
        commandMap.put("uploadAttachment", new UploadAttachmentCommand());
        commandMap.put("deleteAttachment", new DeleteAttachmentCommand());
        commandMap.put("renameAttachment", new RenameAttachmentCommand());
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Set<String> commandKeys = new LinkedHashSet<>();
        String[] optionalCommands = request.getParameterValues("optionalCommand");
        if (optionalCommands != null) {
            for (String commandKey : optionalCommands) {
                logger.debug("adding {} to command keys", commandKey);
                commandKeys.add(commandKey);
            }
        }
        String mainCommand = request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1);
        logger.debug("adding {} to command keys", mainCommand);
        commandKeys.add(mainCommand);

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        for (String key : commandKeys){
            Command command = commandMap.get(key);
            if (command != null) {
                logger.debug("executing {}", command.getClass().getSimpleName());
                command.execute(request,response);
            }else {
                response.sendRedirect("../404.jsp");
            }
        }
    }
}
