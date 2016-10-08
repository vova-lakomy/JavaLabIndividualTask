package com.javalab.contacts.service.command;

import com.javalab.contacts.exception.ConnectionDeniedException;
import com.javalab.contacts.util.LabelsManager;
import com.javalab.contacts.util.PropertiesProvider;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;



import static com.javalab.contacts.util.CustomFileUtils.defineFileName;
import static com.javalab.contacts.util.CustomFileUtils.definePersonalDirectory;


public class UploadPhotoCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(UploadPhotoCommand.class);
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();
    private LabelsManager labelsManager = LabelsManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        Boolean shouldUploadToSpecificDir = Boolean.parseBoolean(properties.getProperty("upload.to.specific.dir"));
        String applicationPath;
        if (shouldUploadToSpecificDir){
            applicationPath = properties.getProperty("specific.upload.dir");
        } else {
            logger.warn("USING TOMCAT CONTEXT DIRECTORY TO STORE UPLOADS. CHECK file-upload.properties");
            applicationPath = request.getServletContext().getRealPath("");
        }
        String relativeUploadPath = properties.getProperty("upload.relative.dir");
        String personalLink = null;
        try {
            personalLink = definePersonalDirectory(request);
        } catch (ConnectionDeniedException e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Could not connect to data base\nContact your system administrator");
            } catch (IOException e1) {
                logger.error("", e1);
            }
        }
        String imagesFolder = properties.getProperty("contact.photo.folder.name");
        String personalImagePath = personalLink + File.separator + imagesFolder + File.separator;

        String uploadImagePath = applicationPath
                + File.separator
                + relativeUploadPath
                + File.separator
                + personalImagePath;

        File fileSaveDir = new File(uploadImagePath);
        try{
            if (!fileSaveDir.exists()){
                FileUtils.forceMkdir(fileSaveDir);
            }else {
                FileUtils.cleanDirectory(fileSaveDir);
            }
        } catch (IOException e) {
            logger.error("failed to access directory {} {} ", uploadImagePath, e);
            try {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "failed to access directory " + fileSaveDir);
            } catch (IOException e1) {
                logger.error("", e1);
            }
        }

        logger.debug("looking for attached photo in request {}", request);
        try {
            Part part = request.getPart("attachedPhoto");
            logger.debug("found attached photo {}", part.getSubmittedFileName());
            String fileName = defineFileName(part,fileSaveDir);
            part.write(uploadImagePath + File.separator + fileName);
            logger.debug("{} uploaded ", part.getSubmittedFileName());
            request.setAttribute("photoLink", relativeUploadPath + personalImagePath + File.separator + fileName);
        } catch (IOException | ServletException e) {
            logger.error("failed to save image to disk {} {} ", uploadImagePath, e);
            String errorMessage = createFileSaveErrorMessage(request);

            try {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, errorMessage);
            } catch (IOException e1) {
                logger.error("", e1);
            }
        }
        return "";
    }

    private String createFileSaveErrorMessage(HttpServletRequest request) {
        Map<String, String> labels = labelsManager.getLabels((String) request.getSession().getAttribute("localeKey"));
        return labels.get("message.photo.save.failed");
    }
}
