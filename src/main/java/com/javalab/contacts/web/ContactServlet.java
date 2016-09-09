package com.javalab.contacts.web;

import com.javalab.contacts.controller.FrontController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.javalab.contacts.util.SqlScriptLoader.*;

@WebServlet(loadOnStartup = 1, urlPatterns = "/contacts/*")
public class ContactServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ContactServlet.class);
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
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        req.setAttribute("contacts",controller.getAllContacts());
//        req.getRequestDispatcher("WEB-INF/contacts.jsp").forward(req,resp);
        req.getRequestDispatcher("WEB-INF/contact-edit-form.jsp").forward(req,resp);
    }
}
