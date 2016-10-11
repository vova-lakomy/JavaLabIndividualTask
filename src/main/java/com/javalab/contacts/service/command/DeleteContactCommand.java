package com.javalab.contacts.service.command;

import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.repository.ContactRepository;
import com.javalab.contacts.repository.impl.ContactRepositoryImpl;
import com.javalab.contacts.util.PropertiesProvider;
import com.javalab.contacts.util.UiMessageService;
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
        logger.debug("executing Delete Contact Command");
        Boolean shouldUploadToSpecificDir = Boolean.parseBoolean(properties.getProperty("upload.to.specific.dir"));
        String applicationPath;
        if (shouldUploadToSpecificDir){
            applicationPath = properties.getProperty("specific.upload.dir");
        } else {
            logger.warn("USING TOMCAT CONTEXT DIRECTORY TO STORE UPLOADS. CHECK file-upload.properties");
            applicationPath = request.getServletContext().getRealPath("");
        }
        String relativeUploadPath = "uploads" + File.separator;
        String uploadsFullPath = applicationPath + File.separator + relativeUploadPath + File.separator;
        logger.debug("defining IDs of contacts to delete");
        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {
            logger.debug("found {} contacts to delete", selectedIds.length);
            for (String stringId: selectedIds){
                stringId = trim(stringId);
                Integer id = null;
                if(isNumeric(stringId)){
                    id = Integer.parseInt(stringId);
                }
                if (id != null) {
                    logger.debug("searching for contact files to delete");
                    String personalDir = null;
                    try {
                        personalDir = contactRepository.getPersonalLink(id);
                    } catch (ConnectionFailedException e) {
                        UiMessageService.sendConnectionErrorMessageToUI(request, response);
                    }
                    if (personalDir != null) {
                        logger.debug("found directory to delete {}", (uploadsFullPath + personalDir));
                        File personalDirFullPath = new File(uploadsFullPath + personalDir);
                        try {
                            FileUtils.deleteDirectory(personalDirFullPath);
                        } catch (IOException e) {
                            logger.error("", e);
                            UiMessageService.sendDirectoryAccessErrorToUI(request, response, personalDirFullPath.toString());
                        }
                    }
                    try {
                        logger.debug("deleting contact id=", id);
                        contactRepository.delete(id);
                        logger.debug("contact with id={} deleted", id);
                    } catch (ConnectionFailedException e) {
                        UiMessageService.sendConnectionErrorMessageToUI(request, response);
                    }
                    request.getSession().setAttribute("messageKey","message.contact.delete");
                    request.getSession().setAttribute("showMessage","true");
                } else {
                    logger.debug("id not found, can not perform delete");
                    UiMessageService.prepareDeleteFailedPopUpErrorMessage(request);
                }
            }
        }
        request.getSession().setAttribute("currentPage",request.getParameter("currentPage"));
        logger.debug("execution Delete Contact command ended");
        return "";
    }

}
