package com.javalab.contacts.service.command;


import com.javalab.contacts.exception.ConnectionDeniedException;
import com.javalab.contacts.repository.PhoneRepository;
import com.javalab.contacts.repository.impl.PhoneRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class DeletePhoneCommand implements Command{

    private static final Logger logger = LoggerFactory.getLogger(DeletePhoneCommand.class);
    private PhoneRepository repository = new PhoneRepositoryImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {
            for (String stringId: selectedIds){
                if(isNotBlank(stringId)){
                    Integer id = Integer.parseInt(stringId);
                    try {
                        repository.delete(id);
                    } catch (ConnectionDeniedException e) {
                        try {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                    "Could not connect to data base\nContact your system administrator");
                        } catch (IOException e1) {
                            logger.error("", e1);
                        }
                    }
                }
            }
        }
        return "";
    }
}
