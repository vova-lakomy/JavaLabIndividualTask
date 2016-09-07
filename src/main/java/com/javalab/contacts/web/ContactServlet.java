package com.javalab.contacts.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class ContactServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ContactServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.debug("Contact servlet init");
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.write("<html><head></head><body>");

        writer.write("<h4>");
        writer.write("using service method, request method type : " + req.getMethod());
        writer.write("</h4>");

        writer.write("<h4>");
        writer.write("using service method, requested URI : " + req.getRequestURI());
        writer.write("</h4>");

        writer.write("<h4>");
        writer.write("using service method, query string : " + req.getQueryString());
        writer.write("</h4>");

        writer.write("</body></html>");
    }
}
