package com.javalab.contacts.service.command;


import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.repository.PhoneRepository;
import com.javalab.contacts.repository.impl.PhoneRepositoryImpl;
import com.javalab.contacts.util.UiMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class DeletePhoneCommand implements Command{

    private static final Logger logger = LoggerFactory.getLogger(DeletePhoneCommand.class);
    private PhoneRepository repository = new PhoneRepositoryImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("executing Delete Phone command");
        logger.debug("searching for phone IDs to delete");
        String[] selectedIds = request.getParameterValues("selectedPhoneId");
        if (selectedIds != null) {
            logger.debug("found {} phone numbers to delete", selectedIds.length);
            for (String stringId: selectedIds){
                if(isNotBlank(stringId)){
                    Integer id = Integer.parseInt(stringId);
                    try {
                        logger.debug("deleting phone with id = {}", id);
                        repository.delete(id);
                        logger.debug("phone number with id={} deleted", id);
                    } catch (ConnectionFailedException e) {
                        UiMessageService.sendConnectionErrorMessageToUI(request, response);
                    }
                }
            }
        }
        logger.debug("execution of Delete Phone command finished");
        return "";
    }
}
