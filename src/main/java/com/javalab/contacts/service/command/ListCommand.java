package com.javalab.contacts.service.command;


import com.javalab.contacts.dto.ContactShortDTO;
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

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.trim;

public class ListCommand implements Command{
    private static final Logger logger = LoggerFactory.getLogger(ListCommand.class);
    private ContactRepository contactRepository = new ContactRepositoryImpl();
    private LabelsManager labelsManager = LabelsManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response){
        logger.debug("executing List command");
        labelsManager.setLocaleLabelsToSession(request.getSession());
        Integer pageNumber = definePageNumber(request);
        logger.debug("defined page number as - {}", pageNumber);
        Collection<ContactShortDTO> contactList = null;
        Integer numberOfPages = 0;
        try {
            contactList = contactRepository.getContactsList(pageNumber-1);
            numberOfPages = calculateNumberOfPages();
            logger.debug("total number of pages - {}", numberOfPages);
            if (pageNumber > numberOfPages){
                pageNumber = numberOfPages;
                logger.debug("requested number of page is greater then max page number ... querying last page");
                contactList = contactRepository.getContactsList(pageNumber-1);
            }

        } catch (ConnectionDeniedException e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Could not connect to data base\nContact your system administrator");
            } catch (IOException e1) {
                logger.error("", e1);
            }
        }

        request.setAttribute("numberOfPages",numberOfPages);
        request.setAttribute("contactsList", contactList);
        request.getSession().setAttribute("currentPage",pageNumber);
        return "contact-list-form.jsp";
    }

    private Integer definePageNumber(HttpServletRequest request){
        String page = request.getParameter("page");
        if (page == null){
            page = String.valueOf(request.getSession().getAttribute("currentPage"));
        }
        page = trim(page);
        int pageNumber = 1;
        if (isNumeric(page)){
            try {
                pageNumber = Integer.parseInt(page);
            } catch (NumberFormatException e){
                pageNumber = 1;
            }
            if (pageNumber < 1){
                pageNumber = 1;
            }
        }
        return pageNumber;
    }

    private Integer calculateNumberOfPages(){
        logger.debug("calculating total number of pages");
        Integer recordsFound = contactRepository.getNumberOfRecordsFound();
        Integer rowsPerPage = contactRepository.getRowsPePageCount();
        int numberOfPages = (int) Math.ceil(recordsFound*1.00/rowsPerPage);
        return numberOfPages;
    }
}
