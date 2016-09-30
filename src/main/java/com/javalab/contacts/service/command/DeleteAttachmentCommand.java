package com.javalab.contacts.service.command;

import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.repository.AttachmentDtoRepository;
import com.javalab.contacts.repository.impl.AttachmentDtoRepositoryImpl;
import com.javalab.contacts.util.CustomFileUtils;
import com.javalab.contacts.util.PropertiesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

public class DeleteAttachmentCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(DeleteAttachmentCommand.class);
    private AttachmentDtoRepository repository = new AttachmentDtoRepositoryImpl();
    private static Properties properties = PropertiesProvider.getInstance().getFileUploadProperties();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String applicationPath = request.getServletContext().getRealPath("");
        Boolean shouldUploadToSpecificDir = Boolean.parseBoolean(properties.getProperty("upload.to.specific.dir"));
        if (shouldUploadToSpecificDir){
            applicationPath = properties.getProperty("specific.upload.dir");
        }

        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {
            for (String stringId : selectedIds){
                Integer id = Integer.parseInt(stringId);
                AttachmentDTO attachmentDTO = repository.get(id);
                String attachmentLink = attachmentDTO.getAttachmentLink();
                String fullPath = applicationPath + attachmentLink;
                CustomFileUtils.deleteFile(fullPath);
                repository.delete(id);
            }
        }
    }
}
