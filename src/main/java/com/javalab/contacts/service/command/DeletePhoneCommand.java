package com.javalab.contacts.service.command;


import com.javalab.contacts.dao.PhoneNumberDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcPhoneNumberDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class DeletePhoneCommand implements Command{

    private PhoneNumberDao numberDao = new JdbcPhoneNumberDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {
            Arrays.stream(selectedIds).forEach(id -> numberDao.delete(Integer.parseInt(id)));  // FIXME: 20.09.16 make via DTO
        }
    }
}
