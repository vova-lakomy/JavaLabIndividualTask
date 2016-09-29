package com.javalab.contacts.service.command;

import com.javalab.contacts.dto.AttachmentDTO;
import com.javalab.contacts.repository.AttachmentDtoRepository;
import com.javalab.contacts.repository.impl.AttachmentDtoRepositoryImpl;
import com.javalab.contacts.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DeleteAttachmentCommand implements Command {

    private static final Logger logger = LoggerFactory.getLogger(DeleteAttachmentCommand.class);
    private AttachmentDtoRepository repository = new AttachmentDtoRepositoryImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String applicationPath = request.getServletContext().getRealPath("");
        String[] selectedIds = request.getParameterValues("selectedId");

        if (selectedIds != null) {
            for (String stringId : selectedIds){
                Integer id = Integer.parseInt(stringId);
                AttachmentDTO attachmentDTO = repository.get(id);
                String relativePath = attachmentDTO.getAttachmentLink();
                relativePath = relativePath.substring(relativePath.indexOf("..") + 3);  //trim ".." from the beginning
                String fullPath = applicationPath + relativePath;
                FileUtils.deleteFile(fullPath);
                repository.delete(id);
            }
        }
    }
}
