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

import static com.javalab.contacts.util.CustomFileUtils.definePersonalDirectory;


public class UploadAttachmentCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(UploadAttachmentCommand.class);
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String applicationPath = request.getServletContext().getRealPath("");
        String relativeUploadPath = properties.getProperty("upload.relative.dir");
        String personalLink = definePersonalDirectory(request);
        String personalPath = personalLink + File.separator + "att" + File.separator;

        String uploadFilePath = applicationPath
                + File.separator
                + relativeUploadPath
                + File.separator
                + personalPath;

        File fileSaveDir = new File(uploadFilePath);
        try{
            if (!fileSaveDir.exists()){
                FileUtils.forceMkdir(fileSaveDir);
            }
        } catch (IOException e) {
            logger.error("error creating directory {} {}", uploadFilePath, e);
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
                    request.setAttribute("attachmentLink-"+index,
                            relativeUploadPath + personalPath + File.separator + fileName);
                }
            }
        } catch (IOException | ServletException e1) {
           logger.error("{}",e1);
        }

    }

}
