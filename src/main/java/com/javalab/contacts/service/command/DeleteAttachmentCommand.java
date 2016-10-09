package com.javalab.contacts.service.command;

import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.exception.ConnectionDeniedException;
import com.javalab.contacts.repository.AttachmentRepository;
import com.javalab.contacts.repository.impl.AttachmentRepositoryImpl;
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

public class DeleteAttachmentCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(DeleteAttachmentCommand.class);
    private AttachmentRepository repository = new AttachmentRepositoryImpl();
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("executing Delete Attachment Command");
        Boolean shouldUploadToSpecificDir = Boolean.parseBoolean(properties.getProperty("upload.to.specific.dir"));
        String applicationPath;
        if (shouldUploadToSpecificDir){
            applicationPath = properties.getProperty("specific.upload.dir");
        } else {
            logger.warn("USING TOMCAT CONTEXT DIRECTORY TO STORE UPLOADS. CHECK file-upload.properties");
            applicationPath = request.getServletContext().getRealPath("");
        }
        logger.debug("defining attachments IDs to delete");
        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {
            logger.debug("found {} IDs to delete", selectedIds.length);
            for (String stringId : selectedIds){
                Integer id = Integer.parseInt(stringId);
                AttachmentDTO attachmentDTO;
                try {
                    logger.debug("trying to delete selected attachments");
                    attachmentDTO = repository.get(id);
                    String attachmentLink = attachmentDTO.getAttachmentLink();
                    String fullPath = applicationPath + attachmentLink;
                    File fileToDelete = new File(fullPath);
                    File parentDirectory = fileToDelete.getParentFile();
                    logger.debug("directory to delete - [{}]", parentDirectory);
                    try {
                        FileUtils.deleteDirectory(parentDirectory);
                        logger.debug("contact attachments deleted");
                    } catch (IOException e) {
                        logger.error("error while deleting file {} \n{}", fileToDelete, e);
                        UiMessageService.sendDirectoryAccessErrorToUI(request, response, fileToDelete.toString());
                    }
                    repository.delete(id);
                } catch (ConnectionDeniedException e) {
                    UiMessageService.sendConnectionErrorMessageToUI(request, response);
                }
            }
        }
        logger.debug("execution Delete Attachment command ended");
        return "";
    }
}
