package com.javalab.contacts.service.command;


import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.repository.ContactDtoRepository;
import com.javalab.contacts.repository.impl.ContactDtoRepositoryImpl;
import com.javalab.contacts.util.PropertiesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ListCommand implements Command{
    private static final Logger logger = LoggerFactory.getLogger(ListCommand.class);
    private ContactDtoRepository contactRepository = new ContactDtoRepositoryImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response){
        String page = request.getParameter("page");
        int pageNumber = 1;
        if (isNotBlank(page)){
            pageNumber = Integer.parseInt(page);
            if (pageNumber < 1){
                pageNumber = 1;
            }
        }
        Collection<ContactShortDTO> contactList = contactRepository.getContactsList(pageNumber-1);
        Integer recordsFound = contactRepository.getNumberOfRecordsFound();
        Integer rowsPerPage = contactRepository.getRowsPePageCount();
        int numberOfPages = (int) Math.ceil(recordsFound*1.00/rowsPerPage);

        request.setAttribute("numberOfPages",numberOfPages);
        request.setAttribute("contactsList", contactList);
        request.setAttribute("currentPage",pageNumber);
        request.setAttribute("path","contact-list-form.jsp");
        try {
            request.getRequestDispatcher("/WEB-INF/app.jsp").forward(request,response);
        } catch (Exception e) {
            logger.error("{}",e);
        }
    }
}
