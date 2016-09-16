package com.javalab.contacts.web;

import com.javalab.contacts.service.AppController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.javalab.contacts.util.SqlScriptLoader.*;

@MultipartConfig
@WebServlet(loadOnStartup = 1, urlPatterns = {"/contacts/*"})
public class FrontServlet extends HttpServlet {


    private static final Logger logger = LogManager.getLogger(FrontServlet.class);
    private AppController appController = new AppController();

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.debug("App servlet init");
        super.init(config);
        loadScript(getServletContext().getRealPath("./WEB-INF/classes/initDB.sql"));
        loadScript(getServletContext().getRealPath("./WEB-INF/classes/populateDB.sql"));
    }

    @Override
    public void destroy() {
        logger.debug("Destroying App servlet");
        super.destroy();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        appController.processRequest(req,resp);
    }
}
