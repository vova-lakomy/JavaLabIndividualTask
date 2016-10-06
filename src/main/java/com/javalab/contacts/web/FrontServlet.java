package com.javalab.contacts.web;

import com.javalab.contacts.service.CommandHelper;
import com.javalab.contacts.service.command.Command;
import com.javalab.contacts.util.PropertiesProvider;
import com.javalab.contacts.util.QuartzScheduler;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import static com.javalab.contacts.util.SqlScriptLoader.loadScript;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@MultipartConfig(maxFileSize = 1024*1024*10)     //10mb
@WebServlet(loadOnStartup = 1, urlPatterns = {"/contacts/*"})
public class FrontServlet extends HttpServlet {


    private static final Logger logger = LoggerFactory.getLogger(FrontServlet.class);
    private CommandHelper commandHelper = new CommandHelper();
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("received request method get");
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("received request method post");
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("utf-8");
        try {
            checkForExceededSize(request, response);
        } catch (Exception e) {
            logger.error("{}",e);
            return;
        }
        Set<String> commandKeys = new LinkedHashSet<>();
        logger.debug("searching for command keys....");
        String[] optionalCommands = request.getParameterValues("optionalCommand");
        if (optionalCommands != null) {
            for (String commandKey : optionalCommands) {
                logger.debug("adding {} to command keys", commandKey);
                commandKeys.add(commandKey);
            }
        }
        String mainCommand = request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1);
        logger.debug("adding {} to command keys", mainCommand);
        commandKeys.add(mainCommand);
        logger.debug("commands to execute: {}", commandKeys);

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = "";
        for (String key : commandKeys){
            Command command = commandHelper.getCommand(key);
            if (command != null) {
                path = command.execute(request,response);
                logger.debug("{} command execution returned '{}'", key, path);
            }
        }
        dispatch(request, response, path);
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response, String path)
                                                         throws  javax.servlet.ServletException, java.io.IOException {
        if (isBlank(path)){
            logger.debug("forward path is not defined, defining redirect URL...");
            String redirectURL = (String) request.getAttribute("redirectURL");
            if (isNotBlank(redirectURL)){
                response.sendRedirect(redirectURL);
                logger.debug("redirected to {}", redirectURL);
            } else {
                response.sendRedirect("list");
                logger.debug("redirected to main view");
            }
        } else {
            logger.debug("defined forward path as '{}'", path);
            request.setAttribute("path",path);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/app.jsp");
            dispatcher.forward(request, response);
            logger.debug("forwarded to app.jsp with path {}", path);
        }
    }

    private void checkForExceededSize(HttpServletRequest req, HttpServletResponse response) throws Exception {
        try {
            String contentType = req.getContentType();
            if (contentType!=null && contentType.toLowerCase().startsWith("multipart")){
                req.getParts();
            }
        } catch (Exception e){
            response.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE,"File size exceeds maximum allowed");
            throw new Exception("File size exceeds maximum allowed");
        }

    }
}
