package com.javalab.contacts.service.command;


import com.javalab.contacts.util.CustomFileUtils;
import com.javalab.contacts.util.PropertiesProvider;
import com.javalab.contacts.util.UiMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class RenameAttachmentCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(RenameAttachmentCommand.class);
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("executing Rename Attachment command");
        Boolean shouldUploadToSpecificDir = Boolean.parseBoolean(properties.getProperty("upload.to.specific.dir"));
        String applicationPath;
        if (shouldUploadToSpecificDir){
            applicationPath = properties.getProperty("specific.upload.dir");
        } else {
            logger.warn("USING TOMCAT CONTEXT DIRECTORY TO STORE UPLOADS. CHECK file-upload.properties");
            applicationPath = request.getServletContext().getRealPath("");
        }
        logger.debug("upload dir defined as {}", applicationPath);

        List<String> fileNames = new ArrayList<>();
        try {
            logger.debug("looking for part with changed file names");
            request.getParts().forEach(part -> {
                if (part.getName().contains("attachmentOldFileName")) {
                    fileNames.add(part.getName());
                }
            });
            logger.debug("found {} files to rename", fileNames.size());
        } catch (IOException | ServletException e) {
            logger.error("{}", e);
            UiMessageService.sendAttachmentProcessErrorToUI(request, response);
        }
        for (String fileName : fileNames){
            logger.debug("renaming file {}", fileName);
            String attachmentIndex = fileName.substring(fileName.lastIndexOf('-'));
            String newFileName = request.getParameter("attachmentFileName" + attachmentIndex);
            String oldFileName = request.getParameter("attachmentOldFileName" + attachmentIndex);
            if (isBlank(newFileName)) {
                logger.debug("new file name is empty, setting as 'no-name'");
                newFileName = "no-name";
            }
            String newAttachmentLink = request.getParameter("attachmentLink" + attachmentIndex);
            String oldAttachmentLink = newAttachmentLink.replace(newFileName, oldFileName);
            String newFullPath = applicationPath + File.separator + newAttachmentLink;
            String oldFullPath = applicationPath + File.separator + oldAttachmentLink;
            File newFile = new File(newFullPath);
            File oldFile = new File(oldFullPath);
            CustomFileUtils.renameFile(oldFile, newFile);
            logger.debug("file renamed to [{}]", newFileName);
        }
        logger.debug("execution Rename Attachment command end");
        return "";
    }
}
