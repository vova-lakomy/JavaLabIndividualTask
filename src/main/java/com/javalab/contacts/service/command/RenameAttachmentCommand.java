package com.javalab.contacts.service.command;


import com.javalab.contacts.util.CustomFileUtils;
import com.javalab.contacts.util.PropertiesProvider;
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

import static com.javalab.contacts.util.CustomFileUtils.definePersonalDirectory;

public class RenameAttachmentCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(RenameAttachmentCommand.class);
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String applicationPath = request.getServletContext().getRealPath("");
        Boolean shouldUploadToSpecificDir = Boolean.parseBoolean(properties.getProperty("upload.to.specific.dir"));
        if (shouldUploadToSpecificDir){
            applicationPath = properties.getProperty("specific.upload.dir");
        }
        String relativeUploadPath = properties.getProperty("upload.relative.dir");
        String personalDirectory = definePersonalDirectory(request);
        String attachmentsFolder = properties.getProperty("attachments.folder.name");
        String personalAttachmentPath = personalDirectory + File.separator + attachmentsFolder + File.separator;
        String uploadFilePath = applicationPath
                + relativeUploadPath
                + File.separator
                + personalAttachmentPath;

        List<String> fileNames = new ArrayList<>();
        try {
            request.getParts().forEach(part -> {
                if (part.getName().contains("attachmentOldFileName")){
                    fileNames.add(part.getName());
                }
            });
        } catch (IOException | ServletException e) {
            logger.error("{}",e);
        }
        fileNames.forEach(fileName -> {
            String attachmentIndex = fileName.substring(fileName.lastIndexOf('-'));
            String newFileName = request.getParameter("attachmentFileName"+attachmentIndex);
            String oldFileName = request.getParameter("attachmentOldFileName"+attachmentIndex);
            if (!newFileName.equals(oldFileName)){
                String newFullPath = uploadFilePath + newFileName;
                String oldFullPath = uploadFilePath + oldFileName;
                File newFile = new File(newFullPath);
                File oldFile = new File(oldFullPath);
                CustomFileUtils.renameFile(oldFile,newFile);
            }
        });
        return "";
    }
}
