package com.javalab.contacts.service.command;

import com.javalab.contacts.exception.ConnectionFailedException;
import com.javalab.contacts.util.CustomFileUtils;
import com.javalab.contacts.util.PropertiesProvider;
import com.javalab.contacts.util.UiMessageService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class UploadPhotoCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(UploadPhotoCommand.class);
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("executing Upload photo command");
        Boolean shouldUploadToSpecificDir = Boolean.parseBoolean(properties.getProperty("upload.to.specific.dir"));
        String applicationPath;
        if (shouldUploadToSpecificDir){
            applicationPath = properties.getProperty("specific.upload.dir");
        } else {
            logger.warn("USING TOMCAT CONTEXT DIRECTORY TO STORE UPLOADS. CHECK file-upload.properties");
            applicationPath = request.getServletContext().getRealPath("");
        }
        String relativeUploadPath = "uploads" + File.separator;
        logger.debug("defining image upload path");
        String personalLink = null;
        try {
            personalLink = CustomFileUtils.definePersonalDirectory(request);
        } catch (ConnectionFailedException e) {
            UiMessageService.sendConnectionErrorMessageToUI(request, response);
        }
        String imagesFolder = properties.getProperty("contact.photo.folder.name");
        String personalImagePath = personalLink + File.separator + imagesFolder + File.separator;

        String uploadImagePath = applicationPath
                + File.separator
                + relativeUploadPath
                + File.separator
                + personalImagePath;
        logger.debug("defined image upload path as {}", uploadImagePath);
        logger.debug("looking for attached photo in request {}", request);
        try {
            Part part = request.getPart("attachedPhoto");
            if (part.getSize() > 0){
                File fileSaveDir = new File(uploadImagePath);
                try{
                    logger.debug("trying to create directories");
                    if (!fileSaveDir.exists()){
                        FileUtils.forceMkdir(fileSaveDir);
                    }else {
                        FileUtils.cleanDirectory(fileSaveDir);
                    }
                } catch (IOException e) {
                    logger.error("failed to access directory {} {} ", uploadImagePath, e);
                    UiMessageService.sendDirectoryAccessErrorToUI(request, response, uploadImagePath);
                }
                logger.debug("found attached photo {}", part.getSubmittedFileName());
                String fileName = CustomFileUtils.defineFileName(part,fileSaveDir);
                logger.debug("writing file to disk");
                part.write(uploadImagePath + File.separator + fileName);
                logger.debug("{} uploaded ", part.getSubmittedFileName());
                String photoLink = relativeUploadPath + personalImagePath + File.separator + fileName;
                request.setAttribute("photoLink", photoLink);
            }
        } catch (IOException | ServletException e) {
            logger.error("failed to save image to disk {} {} ", uploadImagePath, e);
            UiMessageService.sendPhotoProcessErrorToUI(request, response);
        }
        logger.debug("execution Upload Photo command finished");
        return "";
    }
}
