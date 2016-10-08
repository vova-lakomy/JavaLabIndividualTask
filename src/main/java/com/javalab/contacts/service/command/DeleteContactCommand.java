package com.javalab.contacts.service.command;

import com.javalab.contacts.exception.ConnectionDeniedException;
import com.javalab.contacts.repository.ContactRepository;
import com.javalab.contacts.repository.impl.ContactRepositoryImpl;
import com.javalab.contacts.util.PropertiesProvider;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.trim;


public class DeleteContactCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(DeleteContactCommand.class);
    private ContactRepository contactRepository = new ContactRepositoryImpl();
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String applicationPath = request.getServletContext().getRealPath("");
        Boolean shouldUploadToSpecificDir = Boolean.parseBoolean(properties.getProperty("upload.to.specific.dir"));
        if (shouldUploadToSpecificDir){
            applicationPath = properties.getProperty("specific.upload.dir");
        }
        String relativeUploadPath = properties.getProperty("upload.relative.dir");
        String uploadsFullPath = applicationPath + File.separator + relativeUploadPath + File.separator;

        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {
            for (String stringId: selectedIds){
                stringId = trim(stringId);
                Integer id = null;
                if(isNumeric(stringId)){
                    id = Integer.parseInt(stringId);
                }
                if (id != null) {
                    String personalDir = null;
                    try {
                        personalDir = contactRepository.getPersonalLink(id);
                    } catch (ConnectionDeniedException e) {
                        try {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                    "Could not connect to data base\nContact your system administrator");
                        } catch (IOException e1) {
                            logger.error("", e1);
                        }
                    }
                    if (personalDir != null) {
                        File personalDirFullPath = new File(uploadsFullPath + personalDir);
                        try {
                            FileUtils.deleteDirectory(personalDirFullPath);
                        } catch (IOException e) {
                            logger.error("{}",e);
                            try {
                                response.sendError(HttpServletResponse.SC_FORBIDDEN, "failed to delete contact attachments directory");
                            } catch (IOException e1) {
                                logger.error("", e1);
                            }
                        }
                    }
                    try {
                        contactRepository.delete(id);
                    } catch (ConnectionDeniedException e) {
                        try {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                    "Could not connect to data base\nContact your system administrator");
                        } catch (IOException e1) {
                            logger.error("", e1);
                        }
                    }
                    request.getSession().setAttribute("messageKey","message.contact.delete");
                    request.getSession().setAttribute("showMessage","true");
                } else {
                    logger.debug("selected id is 'null' can not perform delete");
                    try {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "can not perfom delete, selected contact does not exist");
                    } catch (IOException e) {
                        logger.error("", e);
                    }

                }
            }
        }
        request.getSession().setAttribute("currentPage",request.getParameter("currentPage"));
        return "";
    }

}
