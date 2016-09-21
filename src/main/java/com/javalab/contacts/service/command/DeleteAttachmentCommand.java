package com.javalab.contacts.service.command;

import com.javalab.contacts.dao.ContactAttachmentDao;
import com.javalab.contacts.dao.impl.jdbc.JdbcContactAttachmentDao;
import com.javalab.contacts.util.FileUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


public class DeleteAttachmentCommand implements Command {

    private ContactAttachmentDao attachmentDao = new JdbcContactAttachmentDao();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String applicationPath = request.getServletContext().getRealPath("");
        String[] selectedIds = request.getParameterValues("selectedId");
        if (selectedIds != null) {;
            Arrays.stream(selectedIds).forEach(id -> {
                String attachmentLink = attachmentDao.get(Integer.parseInt(id)).getAttachmentLink();
                attachmentLink = attachmentLink.substring(attachmentLink.indexOf("..")+3);
                attachmentLink = applicationPath + attachmentLink;
                FileUtils.deleteFile(attachmentLink);
                attachmentDao.delete(Integer.parseInt(id));
            });  // FIXME: 20.09.16 make via DTO
        }
    }
}
