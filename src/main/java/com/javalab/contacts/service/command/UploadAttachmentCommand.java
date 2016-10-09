package com.javalab.contacts.service.command;

import com.javalab.contacts.exception.ConnectionDeniedException;
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

public class UploadAttachmentCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(UploadAttachmentCommand.class);
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("executing Upload Attachment command");
        Boolean shouldUploadToSpecificDir = Boolean.parseBoolean(properties.getProperty("upload.to.specific.dir"));
        logger.debug("defining upload path");
        String attachmentUploadPath;
        if (shouldUploadToSpecificDir){
            attachmentUploadPath = properties.getProperty("specific.upload.dir");
        } else {
            logger.warn("USING TOMCAT CONTEXT DIRECTORY TO STORE UPLOADS. CHECK file-upload.properties");
            attachmentUploadPath = request.getServletContext().getRealPath("");
        }
        String relativeUploadPath = properties.getProperty("upload.relative.dir");
        String personalDirectory = null;
        try {
            personalDirectory = CustomFileUtils.definePersonalDirectory(request);
        } catch (ConnectionDeniedException e) {
            UiMessageService.sendConnectionErrorMessageToUI(request, response);
        }
        String attachmentsFolder = properties.getProperty("attachments.folder.name");
        String personalAttachmentPath = personalDirectory + File.separator + attachmentsFolder + File.separator;
        logger.debug("upload path defined as {}", attachmentUploadPath);
        try {
            logger.debug("looking for attached files in request {}", request);
            for (Part part : request.getParts()) {
                if (part.getName().contains("attachedFile")) {
                    logger.debug("found attached file {}", part.getSubmittedFileName());
                    String attachmentIndex = part.getName().substring(part.getName().lastIndexOf('-') + 1);
                    String randomFileDir =  CustomFileUtils.getFileRandomDir();
                    String uploadFilePath = attachmentUploadPath
                            + relativeUploadPath
                            + File.separator
                            + personalAttachmentPath
                            + File.separator
                            + randomFileDir;
                    File fileSaveDir = new File(uploadFilePath);
                    logger.debug("path to upload attachment defined as {}.. trying to create directories", uploadFilePath);
                    try {
                        if (!fileSaveDir.exists()) {
                            FileUtils.forceMkdir(fileSaveDir);
                        }
                    } catch (IOException e) {
                        logger.error("", e);
                        UiMessageService.sendDirectoryAccessErrorToUI(request, response, fileSaveDir.toString());
                    }
                    String fileName = CustomFileUtils.defineAttachmentFileName(request, attachmentIndex, uploadFilePath);
                    logger.debug("defining attachment file name ");
                    String fullUploadPath = uploadFilePath
                            + File.separator
                            + fileName;
                    logger.debug("attachment file name defined as [{}], try to write file", fullUploadPath);
                    CustomFileUtils.writePartToDisk(part, fullUploadPath);
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
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e1.getMessage());
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        logger.debug("execution of Upload Attachment command end");
        return "";
    }
}
