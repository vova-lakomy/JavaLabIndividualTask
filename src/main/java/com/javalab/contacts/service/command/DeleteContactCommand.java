package com.javalab.contacts.service.command;

import com.javalab.contacts.repository.ContactDtoRepository;
import com.javalab.contacts.repository.impl.ContactDtoRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DeleteContactCommand implements Command {

    private ContactDtoRepository contactRepository = new ContactDtoRepositoryImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {
            for (String stringId: selectedIds){
                Integer id = Integer.parseInt(stringId);
                contactRepository.delete(id);
            }
        }
        try {
            response.sendRedirect("list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
