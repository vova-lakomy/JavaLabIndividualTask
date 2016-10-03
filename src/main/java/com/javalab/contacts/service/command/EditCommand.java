package com.javalab.contacts.service.command;

import com.javalab.contacts.dto.ContactFullDTO;
import com.javalab.contacts.repository.ContactDtoRepository;
import com.javalab.contacts.repository.impl.ContactDtoRepositoryImpl;
import com.javalab.contacts.util.LabelsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class EditCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(EditCommand.class);
    private ContactDtoRepository contactRepository = new ContactDtoRepositoryImpl();
    LabelsManager labelsManager = LabelsManager.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        labelsManager.setLocaleLabelsToSession(request.getSession());
        Integer contactId;
        String contactIdStr = request.getParameter("contactId");
        if (isNotBlank(contactIdStr)) {
            contactId = Integer.valueOf(contactIdStr);
            ContactFullDTO contactFullInfo = contactRepository.getContactFullInfo(contactId);
            request.setAttribute("fullContactInfo", contactFullInfo);
        }
        Collection<String> sexList = contactRepository.getSexList();
        Collection<String> maritalStatusList = contactRepository.getMaritalStatusList();
        Collection<String> phoneTypeList = contactRepository.getPhoneTypeList();
        request.setAttribute("sexList", sexList);
        request.setAttribute("maritalStatusList", maritalStatusList);
        request.setAttribute("phoneTypeList", phoneTypeList);
        request.setAttribute("path","contact-edit-form.jsp");

        try {
            request.getRequestDispatcher("/WEB-INF/app.jsp").forward(request,response);
        } catch (Exception e) {
            logger.error("{}",e);
        }

    }
}
