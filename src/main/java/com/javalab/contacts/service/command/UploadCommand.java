package com.javalab.contacts.service.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;


public class UploadCommand implements Command {
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
        //Get all the parts from request and write it to the file on server
        try {
            //for (Part part : request.getParts()) {
                Part part = request.getPart("data");
                fileName = getFileName(part);
                part.write(uploadFilePath + File.separator + fileName);
                request.setAttribute("photoLink","../resources/uploads/contact_photos/" + fileName);
            //}
        } catch (IOException | ServletException e1) {
            e1.printStackTrace();
        }
    }
    /**
     * Utility method to get file name from HTTP header content-disposition
     */
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= " + contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }

}
