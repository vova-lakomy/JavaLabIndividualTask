package com.javalab.contacts.service.command;


import com.javalab.contacts.repository.PhoneDtoRepository;
import com.javalab.contacts.repository.impl.PhoneDtoRepositoryImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class DeletePhoneCommand implements Command{

    private PhoneDtoRepository repository = new PhoneDtoRepositoryImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {
            for (String stringId: selectedIds){
                if(isNotBlank(stringId)){
                    Integer id = Integer.parseInt(stringId);
                    repository.delete(id);
                }
            }
        }
    }
}
