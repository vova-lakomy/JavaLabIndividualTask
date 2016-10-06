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

import static com.javalab.contacts.util.CustomFileUtils.defineAttachmentFileName;
import static com.javalab.contacts.util.CustomFileUtils.definePersonalDirectory;
import static com.javalab.contacts.util.CustomFileUtils.getFileRandomDir;
import static com.javalab.contacts.util.CustomFileUtils.writePartToDisk;


public class UploadAttachmentCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(UploadAttachmentCommand.class);
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String applicationPath = request.getServletContext().getRealPath("");
        Boolean shouldUploadToSpecificDir = Boolean.parseBoolean(properties.getProperty("upload.to.specific.dir"));
        if (shouldUploadToSpecificDir) {
            applicationPath = properties.getProperty("specific.upload.dir");
        }
        String relativeUploadPath = properties.getProperty("upload.relative.dir");
        String personalDirectory = definePersonalDirectory(request);
        String attachmentsFolder = properties.getProperty("attachments.folder.name");
        String personalAttachmentPath = personalDirectory + File.separator + attachmentsFolder + File.separator;
        String randomFileDir =  getFileRandomDir();

        String uploadFilePath = applicationPath
                + relativeUploadPath
                + File.separator
                + personalAttachmentPath
                + File.separator
                + randomFileDir;

        File fileSaveDir = new File(uploadFilePath);
        try {
            if (!fileSaveDir.exists()) {
                FileUtils.forceMkdir(fileSaveDir);
            }
        } catch (IOException e) {
            logger.error("error creating directory {} {}", uploadFilePath, e);
        }

        try {
            logger.debug("looking for attached files in request {}", request);
            for (Part part : request.getParts()) {
                if (part.getName().contains("attachedFile")) {
                    logger.debug("found attached file {}", part.getSubmittedFileName());
                    String attachmentIndex = part.getName().substring(part.getName().lastIndexOf('-') + 1);
                    String fileName = defineAttachmentFileName(request, attachmentIndex, uploadFilePath);
                    String fullUploadPath = uploadFilePath
                            + File.separator
                            + fileName;
                    writePartToDisk(part, fullUploadPath);
                    logger.debug("{} uploaded ", part.getSubmittedFileName());
                    String attachmentLink = relativeUploadPath
                            + personalAttachmentPath
                            + File.separator
                            + randomFileDir
                            + File.separator
                            + fileName;
                    request.setAttribute("attachmentLink-" + attachmentIndex, attachmentLink);
                }
            }
        } catch (IOException | ServletException e1) {
            logger.error("{}", e1);
        }
        return "";
    }
}
