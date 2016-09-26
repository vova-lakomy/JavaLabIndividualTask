package com.javalab.contacts.service.command;

import com.javalab.contacts.util.PropertiesProvider;
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
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String applicationPath = request.getServletContext().getRealPath("");
        String relativeUploadPath = properties.getProperty("photo.upload.relative.dir");
        String uploadFilePath = applicationPath + File.separator + relativeUploadPath;

        // creates upload directory if it does not exists
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        logger.debug("looking for attached photo in request " + request);
        try {
            Part part = request.getPart("attachedPhoto");
            logger.debug("found attached photo " + part.getSubmittedFileName());
            String fileName = request.getParameter("lastName") + "-photo";
            part.write(uploadFilePath + File.separator + fileName);
            logger.debug(part.getSubmittedFileName() + " uploaded ");
            request.setAttribute("photoLink", ".." + File.separator + relativeUploadPath + File.separator + fileName);
        } catch (IOException | ServletException e1) {
            e1.printStackTrace();
        }
    }
}
