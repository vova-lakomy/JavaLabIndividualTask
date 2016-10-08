package com.javalab.contacts.service.command;

import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.exception.ConnectionDeniedException;
import com.javalab.contacts.repository.ContactRepository;
import com.javalab.contacts.repository.impl.ContactRepositoryImpl;
import com.javalab.contacts.util.LabelsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class EditCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(EditCommand.class);
    private ContactRepository contactRepository = new ContactRepositoryImpl();
    private LabelsManager labelsManager = LabelsManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        labelsManager.setLocaleLabelsToSession(request.getSession());
        Integer contactId;
        String contactIdStr = request.getParameter("contactId");
        if (isNotBlank(contactIdStr)) {
            contactId = Integer.valueOf(contactIdStr);
            ContactFullDTO contactFullInfo = null;
            try {
                contactFullInfo = contactRepository.getContactFullInfo(contactId);
            } catch (ConnectionDeniedException e) {
                try {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                            "Could not connect to data base\nContact your system administrator");
                } catch (IOException e1) {
                    logger.error("", e1);
                }
            }
            request.setAttribute("fullContactInfo", contactFullInfo);
        }
        Collection<String> sexList = contactRepository.getSexList();
        Collection<String> maritalStatusList = contactRepository.getMaritalStatusList();
        Collection<String> phoneTypeList = contactRepository.getPhoneTypeList();
        request.setAttribute("sexList", sexList);
        request.setAttribute("maritalStatusList", maritalStatusList);
        request.setAttribute("phoneTypeList", phoneTypeList);
        return "contact-edit-form.jsp";
    }
}
