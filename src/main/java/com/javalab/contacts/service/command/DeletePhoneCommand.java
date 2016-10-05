package com.javalab.contacts.service.command;


import com.javalab.contacts.repository.PhoneDtoRepository;
import com.javalab.contacts.repository.impl.PhoneDtoRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class DeletePhoneCommand implements Command{

    private static final Logger logger = LoggerFactory.getLogger(DeletePhoneCommand.class);
    private PhoneDtoRepository repository = new PhoneDtoRepositoryImpl();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {
            for (String stringId: selectedIds){
                if(isNotBlank(stringId)){
                    Integer id = Integer.parseInt(stringId);
                    repository.delete(id);
                }
            }
        }
        return "";
    }
}
