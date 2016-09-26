package com.javalab.contacts.service;

import com.javalab.contacts.service.command.Command;
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

import static org.apache.commons.lang3.StringUtils.capitalize;

public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    private Map<String,Command> commandMap = new HashMap<>();


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
            Command command = getCommand(key);
            if (command != null) {
                logger.debug("executing {}", command.getClass().getSimpleName());
                command.execute(request,response);
            }else {
                response.sendRedirect("../404.jsp");
            }
        }
    }

    private Command getCommand(String key) {
        logger.debug("trying to get command from map by key '{}'",key);
        Command command = commandMap.get(key);
        if (command != null){
            logger.debug("got command {}  from map",command);
            return command;
        } else {
            logger.debug("not found command by key '{}' ... trying to create one",key);
            command = createCommand(key);
            if (command != null) {
                logger.debug("successfully created command {} , adding to map", command);
                commandMap.put(key,command);
            }
            logger.debug("returning - {}", command);
            return command;
        }
    }

    private Command createCommand(String key) {
        String packageName = Command.class.getName();
        String className = capitalize(key) + "Command";
        String fullClassName = packageName.replace("Command",className);
        Command command = null;
        try {
            logger.debug("trying to create command instance for class name {}",fullClassName);
            Class<?> clazz = Class.forName(fullClassName);
            command = (Command) clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            logger.error("creating command for class name {} failed \n\t {}",fullClassName,e.getMessage());
        }
        return command;
    }
}
