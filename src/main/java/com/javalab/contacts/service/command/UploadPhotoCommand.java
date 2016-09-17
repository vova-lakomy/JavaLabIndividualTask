package com.javalab.contacts.service.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;


public class UploadPhotoCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String applicationPath = request.getServletContext().getRealPath("");
        // constructs path of the directory to save uploaded file
        String uploadFilePath = applicationPath + File.separator + "resources/uploads/contact_photos";

        // creates the save directory if it does not exists
        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        String fileName = null;
        try {
            //for (Part part : request.getParts()) {
                Part part = request.getPart("attachedPhoto");
//                fileName = getFileName(part);
                fileName = request.getParameter("lastName") + "-photo";
                part.write(uploadFilePath + File.separator + fileName);
                request.setAttribute("photoLink","../resources/uploads/contact_photos/" + fileName);
            //}
        } catch (IOException | ServletException e1) {
            e1.printStackTrace();
        }
    }

    private String getFileName(Part part) {

        String contentDispositionHeader = part.getHeader("content-disposition");
        String[] properties = contentDispositionHeader.split(";");
        for (String prop : properties) {
            if (prop.trim().startsWith("filename")) {
                return prop.substring(prop.indexOf("=") + 2, prop.length() - 1);
            }
        }
        return "";
    }

}
