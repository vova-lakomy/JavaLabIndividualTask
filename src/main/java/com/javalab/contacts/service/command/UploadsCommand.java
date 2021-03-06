package com.javalab.contacts.service.command;

import com.javalab.contacts.util.PropertiesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


public class UploadsCommand implements Command {
    private static final int BUFFER_LENGTH = 4096;
    private static final Logger logger = LoggerFactory.getLogger(UploadsCommand.class);

    private Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("executing Uploads command");
        logger.debug("defining requested filepath");
        String filePath = String.valueOf(request.getAttribute("uriParams"));
        if (filePath != null && !filePath.equals("null")) {
            Boolean shouldUploadToSpecificDir = Boolean.parseBoolean(properties.getProperty("upload.to.specific.dir"));
            String applicationPath;
            if (shouldUploadToSpecificDir){
                applicationPath = properties.getProperty("specific.upload.dir");
            } else {
                logger.warn("USING TOMCAT CONTEXT DIRECTORY TO STORE UPLOADS. CHECK file-upload.properties");
                applicationPath = request.getServletContext().getRealPath("");
            }
            String uploadsRealPath = applicationPath + File.separator;
            String uploadsRelativePath = "uploads" + File.separator;
            logger.debug("defining requested file");
            File fileToRead = new File(uploadsRealPath + uploadsRelativePath + filePath);
            logger.debug("defined file to read [{}]", fileToRead);
            try (InputStream inputStream = new FileInputStream(fileToRead);
                                        OutputStream outputStream = response.getOutputStream()) {
                response.setContentLength((int) fileToRead.length());
                response.setContentType(new MimetypesFileTypeMap().getContentType(fileToRead));
                byte[] buffer = new byte[BUFFER_LENGTH];
                int data;
                logger.debug("reading file");
                while ((data = inputStream.read(buffer, 0, BUFFER_LENGTH)) != -1) {
                    outputStream.write(buffer, 0, data);
                    outputStream.flush();
                }
                logger.debug("file has been read");
            } catch (IOException e) {
                logger.error("error while reading file {}", fileToRead, e);
            }
        }
        logger.debug("execution of Uploads command finished");
        return "";
    }

}

