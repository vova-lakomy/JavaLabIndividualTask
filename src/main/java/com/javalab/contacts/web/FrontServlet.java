package com.javalab.contacts.web;

import com.javalab.contacts.service.AppController;
import com.javalab.contacts.util.PropertiesProvider;
import com.javalab.contacts.util.QuartzScheduler;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

import static com.javalab.contacts.util.SqlScriptLoader.loadScript;

@MultipartConfig(maxFileSize = 1024*1024*10)     //10mb
@WebServlet(loadOnStartup = 1, urlPatterns = {"/contacts/*"})
public class FrontServlet extends HttpServlet {


    private static final Logger logger = LoggerFactory.getLogger(FrontServlet.class);
    private AppController appController = new AppController();
    private Properties properties = PropertiesProvider.getInstance().getScriptLoaderProperties();
    private Scheduler scheduler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.debug("App servlet init");
        super.init(config);
        Boolean shouldLoadScript = Boolean.valueOf(properties.getProperty("load.scripts.at.start"));
        if(shouldLoadScript){
            loadScript(getServletContext().getRealPath("./WEB-INF/classes/initDB.sql"));
            loadScript(getServletContext().getRealPath("./WEB-INF/classes/populateDB.sql"));
        }
        try {
            scheduler =  StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
           logger.error("{}",e.getMessage());
        }
        QuartzScheduler.start(scheduler);
    }

    @Override
    public void destroy() {
        logger.debug("Destroying App servlet");
        QuartzScheduler.stop(scheduler);
        super.destroy();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        try {
            checkForExceededSize(req);
        } catch (Exception e) {
            logger.error("{}",e);
            resp.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE,"File size exceeds maximum allowed");
            return;
        }
        appController.processRequest(req,resp);
    }

    private void checkForExceededSize(HttpServletRequest req) throws Exception{
        String contentType = req.getContentType();
        if (contentType!=null && contentType.toLowerCase().startsWith("multipart")){
            req.getParts();
        }
    }
}
