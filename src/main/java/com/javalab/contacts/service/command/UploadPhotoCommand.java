package com.javalab.contacts.service.command;

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
import java.util.Properties;



import static com.javalab.contacts.util.CustomFileUtils.defineFileName;
import static com.javalab.contacts.util.CustomFileUtils.definePersonalDirectory;


public class UploadPhotoCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(UploadPhotoCommand.class);
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String applicationPath = request.getServletContext().getRealPath("");
        Boolean shouldUploadToSpecificDir = Boolean.parseBoolean(properties.getProperty("upload.to.specific.dir"));
        if (shouldUploadToSpecificDir){
            applicationPath = properties.getProperty("specific.upload.dir");
        }
        String relativeUploadPath = properties.getProperty("upload.relative.dir");
        String personalLink = definePersonalDirectory(request);
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
            logger.error("error while processing directory {} {}", uploadImagePath, e);
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
           logger.error("{}",e);
        }
    }
}
