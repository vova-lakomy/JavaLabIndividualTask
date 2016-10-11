package com.javalab.contacts.service.command;

import com.javalab.contacts.dto.ContactSearchDTO;
import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.repository.ContactRepository;
import com.javalab.contacts.repository.impl.ContactRepositoryImpl;
import com.javalab.contacts.util.LabelsManager;
import com.javalab.contacts.util.UiMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.trim;

public class SearchCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(SearchCommand.class);
    private ContactRepository contactRepository = new ContactRepositoryImpl();
    private LabelsManager labelsManager = LabelsManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("executing Search command");
        labelsManager.setLocaleLabelsToSession(request.getSession());
        if (request.getParameterMap().size() > 0){
            String query = request.getQueryString();
            logger.debug("saving search query string to perform further pagination");
            String queryNoPage;
            if (query.contains("page=")){
                queryNoPage = query.substring(0, query.lastIndexOf("page=")-1);
            } else {
                queryNoPage = query;
            }
            String searchQueryString = "search?" + queryNoPage + "&page=";
            int pageNumber = 1;
            String strPageNumber = (trim(request.getParameter("page")));
            if (isNotBlank(strPageNumber)){
                try{
                    pageNumber = Integer.valueOf(strPageNumber);
                    if (pageNumber < 1){
                        pageNumber = 1;
                    }
                } catch (NumberFormatException e){
                    pageNumber = 1;
                }
            }
            logger.debug("creating search object instance");
            ContactSearchDTO searchObject = createSearchDTO(request);
            Collection<ContactShortDTO> searchResult = null;
            Integer numberOfPages = 0;
            try {
                logger.debug("searching for requested contacts");
                searchResult = contactRepository.search(searchObject, pageNumber-1);
                logger.debug("search complete");
                numberOfPages = defineNumberOfPages();
                if (pageNumber > numberOfPages){
                    pageNumber = numberOfPages;
                    searchResult = contactRepository.search(searchObject, pageNumber-1);
                }
            } catch (ConnectionFailedException e) {
                UiMessageService.sendConnectionErrorMessageToUI(request, response);
            }
            logger.debug("setting found collection as attribute to request");
            request.setAttribute("numberOfPages",numberOfPages);
            request.setAttribute("searchQueryString",searchQueryString);
            request.setAttribute("currentPage",pageNumber);
            request.setAttribute("searchResult", searchResult);
            request.setAttribute("path","contact-list-form.jsp");
            logger.debug("execution of Search command end");
            return "contact-list-form.jsp";
        } else {
            logger.debug("no search parameters found, returning search form view");
            Collection<String> sexList = contactRepository.getSexList();
            Collection<String> maritalStatusList = contactRepository.getMaritalStatusList();
            request.setAttribute("sexList", sexList);
            request.setAttribute("maritalStatusList", maritalStatusList);
            logger.debug("execution of Search command finished");
            return "contact-search-form.jsp";
        }
    }

    private ContactSearchDTO createSearchDTO(HttpServletRequest request){
        logger.debug("defining search object fields");
        String firstName = trim(request.getParameter("firstName"));
        String lastName = trim(request.getParameter("lastName"));
        String secondName = trim(request.getParameter("secondName"));
        String birthDateBefore = defineDateBefore(request);
        String birthDateAfter = defineDateAfter(request);
        String sex = request.getParameter("sex").toUpperCase();
        String nationality = trim(request.getParameter("nationality"));
        String maritalStatus = request.getParameter("maritalStatus").toUpperCase();
        String country = trim(request.getParameter("country"));
        String town = trim(request.getParameter("town"));
        String street = trim(request.getParameter("street"));

        Integer zipCode = null;
        String strZipCode = trim(request.getParameter("zipCode"));
        if (isNotBlank(strZipCode)){
            zipCode = Integer.parseInt(strZipCode);
        }
        Integer houseNumber = null;
        String strHouseNumber = trim(request.getParameter("houseNumber"));
        if(isNotBlank(strHouseNumber)){
            houseNumber = Integer.valueOf(strHouseNumber);
        }
        Integer flatNumber = null;
        String strFlatNumber = trim(request.getParameter("flatNumber"));
        if (isNotBlank(strFlatNumber)){
            flatNumber = Integer.valueOf(strFlatNumber);
        }
        logger.debug("fields of search object defined, creating instance");
        ContactSearchDTO searchDTO = new ContactSearchDTO();
        searchDTO.setFirstName(firstName);
        searchDTO.setSecondName(secondName);
        searchDTO.setLastName(lastName);
        searchDTO.setDateOfBirthGreaterThan(birthDateAfter);
        searchDTO.setDateOfBirthLessThan(birthDateBefore);
        searchDTO.setSex(sex);
        searchDTO.setNationality(nationality);
        searchDTO.setMaritalStatus(maritalStatus);
        searchDTO.setCountry(country);
        searchDTO.setTown(town);
        searchDTO.setStreet(street);
        searchDTO.setZipCode(zipCode);
        searchDTO.setHouseNumber(houseNumber);
        searchDTO.setFlatNumber(flatNumber);
        searchDTO.setOrderBy("lastName");
        logger.debug("returning {}", searchDTO);
        return searchDTO;
    }

    private String defineDateAfter(HttpServletRequest request) {
        String dayAfter = trim(request.getParameter("dayOfBirthAfter"));
        String monthAfter = trim(request.getParameter("monthOfBirthAfter"));
        String yearAfter =trim(request.getParameter("yearOfBirthAfter"));
        if (isNumeric(dayAfter) && isNumeric(monthAfter) && isNumeric(yearAfter)){
            return yearAfter + "-" + monthAfter + "-" + dayAfter;
        } else if (!isNumeric(dayAfter) && !isNumeric(monthAfter) && isNumeric(yearAfter)){
            return yearAfter + "-01-01";
        } else {
            return null;
        }
    }

    private String defineDateBefore(HttpServletRequest request) {
        String dayBefore = trim(request.getParameter("dayOfBirthBefore"));
        String monthBefore = trim(request.getParameter("monthOfBirthBefore"));
        String yearBefore =trim(request.getParameter("yearOfBirthBefore"));
        if (isNumeric(dayBefore) && isNumeric(monthBefore) && isNumeric(yearBefore)){
            return yearBefore + "-" + monthBefore + "-" + dayBefore;
        } else if (!isNumeric(dayBefore) && !isNumeric(monthBefore) && isNumeric(yearBefore)){
            return yearBefore + "-01-01";
        } else {
            return null;
        }
    }

    private Integer defineNumberOfPages() {
        Integer recordsFound = contactRepository.getNumberOfRecordsFound();
        Integer rowsPerPage = contactRepository.getRowsPePageCount();
        return (int) Math.ceil(recordsFound*1.00/rowsPerPage);
    }
}
