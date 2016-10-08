package com.javalab.contacts.service.command;


import com.javalab.contacts.exception.ConnectionDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
     String execute(HttpServletRequest request, HttpServletResponse response);
}
