package com.javalab.contacts.service.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;


public class UploadAttachmentCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + "resources/uploads/contact_attachments";

        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        try {
            for (Part part : request.getParts()) {
                if (part.getName().contains("attachedFile")){
                    String index = part.getName().substring(part.getName().lastIndexOf('-') + 1);
                    String fileName = request.getParameter("attachmentFileName-" + index);
                    part.write(uploadFilePath + File.separator + fileName);
                    request.setAttribute("attachmentLink-"+index,"../resources/uploads/contact_attachments/" + fileName);
                }
            }
        } catch (IOException | ServletException e1) {
            e1.printStackTrace();
        }

    }

}
