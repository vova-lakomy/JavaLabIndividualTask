package com.javalab.contacts.service.command;

import com.javalab.contacts.repository.AttachmentDtoRepository;
import com.javalab.contacts.repository.ContactDtoRepository;
import com.javalab.contacts.repository.impl.AttachmentDtoRepositoryImpl;
import com.javalab.contacts.repository.impl.ContactDtoRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

public class EditCommand implements Command {

    private ContactDtoRepository contactRepository = new ContactDtoRepositoryImpl();
    private AttachmentDtoRepository attachmentRepository = new AttachmentDtoRepositoryImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        Integer contactId;
        if (request.getParameter("contactId") != null) {
            contactId = Integer.valueOf(request.getParameter("contactId"));
            request.setAttribute("fullContactInfo", contactRepository.getContactFullInfo(contactId));
            request.setAttribute("attachments", attachmentRepository.getByContactId(contactId)); //// FIXME: 27.09.16
        }

        Collection<String> sexList = contactRepository.getSexList();
        Collection<String> martialStatusList = contactRepository.getMartialStatusList();
        Collection<String> phoneTypeList = contactRepository.getPhoneTypeList();

        request.setAttribute("sexList", sexList);
        request.setAttribute("martialStatusList", martialStatusList);
        request.setAttribute("phoneTypeList", phoneTypeList);
        request.setAttribute("path","contact-edit-form.jsp");
        try {
            request.getRequestDispatcher("/WEB-INF/app.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
