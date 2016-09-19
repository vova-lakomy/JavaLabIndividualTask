package com.javalab.contacts.service.command;


import com.javalab.contacts.util.MailSender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MailCommand implements Command {

    private MailSender mailSender = new MailSender();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        if (request.getParameter("action") != null){
            if (request.getParameter("action").equals("compose")){
                System.out.println("compose");
            }
        }else {
            String mailTo = request.getParameter("mailTo");
            String mailTopic = request.getParameter("mailTopic");
            String mailText = request.getParameter("mailText");
            mailSender.sendMail(mailTo, mailTopic, mailText);
        }




        request.setAttribute("path","contact-email-form.jsp");
        try {
            request.getRequestDispatcher("/WEB-INF/app.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
