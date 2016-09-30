package com.javalab.contacts.service.command;

import com.javalab.contacts.repository.ContactDtoRepository;
import com.javalab.contacts.repository.impl.ContactDtoRepositoryImpl;
import com.javalab.contacts.util.PropertiesProvider;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isNumeric;


public class DeleteContactCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(DeleteContactCommand.class);
    private ContactDtoRepository contactRepository = new ContactDtoRepositoryImpl();
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String applicationPath = request.getServletContext().getRealPath("");
        Boolean shouldUploadToSpecificDir = Boolean.parseBoolean(properties.getProperty("upload.to.specific.dir"));
        if (shouldUploadToSpecificDir){
            applicationPath = properties.getProperty("specific.upload.dir");
        }
        String relativeUploadPath = properties.getProperty("upload.relative.dir");
        String uploadsFullPath = applicationPath + File.separator + relativeUploadPath + File.separator;

        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {
            for (String stringId: selectedIds){
                Integer id = null;
                if(isNumeric(stringId)){
                    id = Integer.parseInt(stringId);
                }
                String personalDir = contactRepository.getPersonalLink(id);
                if (personalDir != null) {
                    File personalDirFullPath = new File(uploadsFullPath + personalDir);
                    try {
                        FileUtils.deleteDirectory(personalDirFullPath);
                    } catch (IOException e) {
                        logger.error("{}",e);
                    }
                }
                contactRepository.delete(id);
            }
        }
        try {
            response.sendRedirect("list");
        } catch (IOException e) {
            logger.error("",e);
        }
    }

}
