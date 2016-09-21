package com.javalab.contacts.service.command;


import com.javalab.contacts.util.FileUtils;
import com.javalab.contacts.util.PropertiesProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RenameAttachmentCommand implements Command {
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String applicationPath = request.getServletContext().getRealPath("");
        List<String> fileNames = new ArrayList<>();
        try {
            request.getParts().forEach(part -> {
                if (part.getName().contains("attachmentOldFileName")){
                    fileNames.add(part.getName());
                }
            });
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
        fileNames.forEach(fileName -> {
            String index = fileName.substring(fileName.lastIndexOf('-'));
            String newFileName = request.getParameter("attachmentFileName"+index);
            String oldFileName = request.getParameter("attachmentOldFileName"+index);
            if (!newFileName.equals(oldFileName)){
                String relativePath = properties.getProperty("attachment.upload.relative.dir");
                String newFullPath = applicationPath + relativePath + File.separator + newFileName;
                String oldFullPath = applicationPath + relativePath + File.separator + oldFileName;
                File newFile = new File(newFullPath);
                File oldFile = new File(oldFullPath);
                FileUtils.renameFile(oldFile,newFile);
            }
        });
    }
}
