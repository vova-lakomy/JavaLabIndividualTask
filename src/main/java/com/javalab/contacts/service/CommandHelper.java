package com.javalab.contacts.service;

import com.javalab.contacts.service.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class CommandHelper {
    private static final Logger logger = LoggerFactory.getLogger(CommandHelper.class);
    private Map<String,Command> commandMap = new HashMap<>();

    public Command getCommand(String key) {
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
