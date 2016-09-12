package com.javalab.contacts.web;

import com.javalab.contacts.service.FrontController;
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
    private FrontController frontController = new FrontController();

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        frontController.processRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        frontController.processRequest(request,response);
    }



}
