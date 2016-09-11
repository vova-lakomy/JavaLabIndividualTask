package com.javalab.contacts.web;

import com.javalab.contacts.controller.FrontController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.javalab.contacts.util.SqlScriptLoader.*;

@WebServlet(loadOnStartup = 1, urlPatterns = "/contacts/*")
public class AppServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(AppServlet.class);
    private FrontController controller = new FrontController();

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.debug("Contact servlet init");
        super.init(config);
        loadScript(getServletContext().getRealPath("./WEB-INF/classes/initDB.sql"));
        loadScript(getServletContext().getRealPath("./WEB-INF/classes/populateDB.sql"));
    }

    @Override
    public void destroy() {
        logger.debug("Destroying Contact servlet");
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer contactId = null;
        if (request.getParameter("contactId") != null) {
            contactId = Integer.valueOf(request.getParameter("contactId"));
        }
        response.setContentType("text/html; charset=UTF-8");
        request.setAttribute("phoneNumbers",controller.getPhoneNumbers(contactId));
        request.setAttribute("attachments",controller.getAttachments(contactId));
        request.setAttribute("contacts",controller.getAllContacts());
        String requestURI = request.getRequestURI();
        String form = requestURI.substring(requestURI.lastIndexOf('/')+1);
        request.setAttribute("path",form);
        request.getRequestDispatcher("/WEB-INF/app.jsp").forward(request,response);
    }
}
