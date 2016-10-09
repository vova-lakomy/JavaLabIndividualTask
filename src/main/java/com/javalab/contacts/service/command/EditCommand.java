package com.javalab.contacts.service.command;

import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.exception.ConnectionDeniedException;
import com.javalab.contacts.exception.ContactNotFoundException;
import com.javalab.contacts.repository.ContactRepository;
import com.javalab.contacts.repository.impl.ContactRepositoryImpl;
import com.javalab.contacts.util.LabelsManager;
import com.javalab.contacts.util.UiMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class EditCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(EditCommand.class);
    private ContactRepository contactRepository = new ContactRepositoryImpl();
    private LabelsManager labelsManager = LabelsManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("executing Edit command");
        labelsManager.setLocaleLabelsToSession(request.getSession());
        logger.debug("searching for contact ID");
        String contactIdStr = request.getParameter("contactId");
        Integer contactId;
        if (isNotBlank(contactIdStr)) {
            contactId = Integer.valueOf(contactIdStr);
            logger.debug("found contact id={}... retrieving full info", contactId);
            ContactFullDTO contactFullInfo = null;
            try {
                contactFullInfo = contactRepository.getContactFullInfo(contactId);
                logger.debug("got full info about contact with id={}", contactId);
            } catch (ConnectionDeniedException e) {
                UiMessageService.sendConnectionErrorMessageToUI(request, response);
            } catch (ContactNotFoundException e) {
                logger.debug("", e);
                UiMessageService.sendContactNotFoundErrorToUI(request, response);
            }
            request.setAttribute("fullContactInfo", contactFullInfo);
        }
        Collection<String> sexList = contactRepository.getSexList();
        Collection<String> maritalStatusList = contactRepository.getMaritalStatusList();
        Collection<String> phoneTypeList = contactRepository.getPhoneTypeList();
        request.setAttribute("sexList", sexList);
        request.setAttribute("maritalStatusList", maritalStatusList);
        request.setAttribute("phoneTypeList", phoneTypeList);
        logger.debug("execution of Edit command finished");
        return "contact-edit-form.jsp";
    }
}
