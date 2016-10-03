package com.javalab.contacts.service.command;

import com.javalab.contacts.dto.ContactSearchDTO;
import com.javalab.contacts.dto.ContactShortDTO;
import com.javalab.contacts.repository.ContactDtoRepository;
import com.javalab.contacts.repository.impl.ContactDtoRepositoryImpl;
import com.javalab.contacts.util.LabelsManager;
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
    private ContactDtoRepository contactRepository = new ContactDtoRepositoryImpl();
    private LabelsManager labelsManager = LabelsManager.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        labelsManager.setLocaleLabelsToSession(request.getSession());

        if (request.getParameterMap().size() > 0){
            String query = request.getQueryString();
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
                pageNumber = Integer.valueOf(strPageNumber);
                if (pageNumber < 1){
                    pageNumber = 1;
                }
            }
            ContactSearchDTO searchObject = createSearchDTO(request);
            Collection<ContactShortDTO> searchResult = contactRepository.search(searchObject, pageNumber-1);
            Integer recordsFound = contactRepository.getNumberOfRecordsFound();
            Integer rowsPerPage = contactRepository.getRowsPePageCount();
            int numberOfPages = (int) Math.ceil(recordsFound*1.00/rowsPerPage);

            request.setAttribute("numberOfPages",numberOfPages);
            request.setAttribute("searchQueryString",searchQueryString);
            request.setAttribute("currentPage",pageNumber);
            request.setAttribute("searchResult", searchResult);
            request.setAttribute("path","contact-list-form.jsp");
        } else {
            Collection<String> sexList = contactRepository.getSexList();
            Collection<String> martialStatusList = contactRepository.getMartialStatusList();

            request.setAttribute("sexList", sexList);
            request.setAttribute("martialStatusList", martialStatusList);
            request.setAttribute("path","contact-search-form.jsp");
        }

        try {
            request.getRequestDispatcher("/WEB-INF/app.jsp").forward(request,response);
        } catch (Exception e) {
            logger.error("{}",e);
        }
    }

    private ContactSearchDTO createSearchDTO(HttpServletRequest request){
        String firstName = trim(request.getParameter("firstName"));
        String lastName = trim(request.getParameter("lastName"));
        String secondName = trim(request.getParameter("secondName"));
        String birthDateBefore = defineDateBefore(request);
        String birthDateAfter = defineDateAfter(request);
        String sex = request.getParameter("sex").toUpperCase();
        String nationality = trim(request.getParameter("nationality"));
        String martialStatus = request.getParameter("martialStatus").toUpperCase();
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

        ContactSearchDTO searchDTO = new ContactSearchDTO();
        searchDTO.setFirstName(firstName);
        searchDTO.setSecondName(secondName);
        searchDTO.setLastName(lastName);
        searchDTO.setDateOfBirthGreaterThan(birthDateAfter);
        searchDTO.setDateOfBirthLessThan(birthDateBefore);
        searchDTO.setSex(sex);
        searchDTO.setNationality(nationality);
        searchDTO.setMartialStatus(martialStatus);
        searchDTO.setCountry(country);
        searchDTO.setTown(town);
        searchDTO.setStreet(street);
        searchDTO.setZipCode(zipCode);
        searchDTO.setHouseNumber(houseNumber);
        searchDTO.setFlatNumber(flatNumber);
        searchDTO.setOrderBy("lastName");
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
}
