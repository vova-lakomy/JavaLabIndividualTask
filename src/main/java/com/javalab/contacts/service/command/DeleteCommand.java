package com.javalab.contacts.service.command;

import com.javalab.contacts.dao.ContactDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


public class DeleteCommand implements Command {

    private ContactDao contactDao = new JdbcContactDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {
            Arrays.stream(selectedIds).forEach(id -> contactDao.delete(Integer.parseInt(id)));
        }
        try {
            response.sendRedirect("list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
