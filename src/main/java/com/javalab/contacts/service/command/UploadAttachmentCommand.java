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


public class UploadAttachmentCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(UploadAttachmentCommand.class);
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String applicationPath = request.getServletContext().getRealPath("");
        String relativeUploadPath = properties.getProperty("attachment.upload.relative.dir");
        String uploadFilePath = applicationPath + File.separator + relativeUploadPath;

        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        try {
            logger.debug("looking for attached files in request {}", request);
            for (Part part : request.getParts()) {
                if (part.getName().contains("attachedFile")){
                    logger.debug("found attached file {}", part.getSubmittedFileName());
                    String index = part.getName().substring(part.getName().lastIndexOf('-') + 1);
                    String fileName = request.getParameter("attachmentFileName-" + index);
                    part.write(uploadFilePath + File.separator + fileName);
                    logger.debug("{} uploaded ", part.getSubmittedFileName());
                    request.setAttribute("attachmentLink-"+index,".." + File.separator + relativeUploadPath + File.separator + fileName);
                }
            }
        } catch (IOException | ServletException e1) {
            e1.printStackTrace();
        }

    }

}
