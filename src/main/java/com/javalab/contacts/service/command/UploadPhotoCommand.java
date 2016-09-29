package com.javalab.contacts.service.command;

import com.javalab.contacts.repository.ContactDtoRepository;
import com.javalab.contacts.repository.impl.ContactDtoRepositoryImpl;
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

import static com.javalab.contacts.util.FileUtils.generateRandomString;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;


public class UploadPhotoCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(UploadPhotoCommand.class);
    private ContactDtoRepository repository = new ContactDtoRepositoryImpl();
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();
    private static final int RANDOM_STRING_CHARS_COUNT = 6;


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String applicationPath = request.getServletContext().getRealPath("");
        String relativeUploadPath = properties.getProperty("upload.relative.dir");
//        /home/student/IdeaProjects/ContactsList/target/contacts-list/  + resources/uploads/

        String personalLink = definePersonalLink(request);

        String personalPath = personalLink + File.separator + "img" + File.separator;

        String uploadImagePath = applicationPath
                + File.separator
                + relativeUploadPath
                + File.separator
                + personalPath;

        File fileSaveDir = new File(uploadImagePath);
        if (!fileSaveDir.exists()) {
            logger.debug("directory {} does not exist... creating one", fileSaveDir);
            fileSaveDir.mkdirs();
        }

        logger.debug("looking for attached photo in request {}", request);
        try {
            Part part = request.getPart("attachedPhoto");
            logger.debug("found attached photo {}", part.getSubmittedFileName());
            String fileName = defineFileName(request, part);
            part.write(uploadImagePath + File.separator + fileName);
            logger.debug("{} uploaded ", part.getSubmittedFileName());
            request.setAttribute("photoLink", relativeUploadPath + personalPath + File.separator + fileName);
        } catch (IOException | ServletException e) {
           logger.error("{}",e);
        }
    }

    private String definePersonalLink(HttpServletRequest request) {
        String contactIdString = request.getParameter("contactId");
        String personalLink = null;
        if (isNotBlank(contactIdString)){
            Integer contactId = Integer.parseInt(contactIdString);
            personalLink = repository.getPersonalLink(contactId);
        }
        if (personalLink == null){
            personalLink = request.getParameter("lastName")
                    + generateRandomString(RANDOM_STRING_CHARS_COUNT);
            request.setAttribute("personalLink",personalLink);
        }
        return personalLink;
    }

    private String defineFileName(HttpServletRequest request, Part part) {
        if (part != null) {
            String submittedFileName = part.getSubmittedFileName();
            String personLastName = request.getParameter("lastName");
            if (isBlank(personLastName)){
                return submittedFileName;
            }
            String extension = "";
            if (submittedFileName.contains(".")){
                extension = submittedFileName.substring(submittedFileName.lastIndexOf('.'));
            }
            return personLastName + "-photo" + extension;
        } else {
            return "no-name";
        }
    }

}
