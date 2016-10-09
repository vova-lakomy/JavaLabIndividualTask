package com.javalab.contacts.service.command;

import com.javalab.contacts.util.LabelsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class SetLocaleCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(SetLocaleCommand.class);
    private LabelsManager labelsManager = LabelsManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("executing Set Locale command");
        String localeKey = request.getParameter("localeKey");
        logger.debug("locale defined as [{}]", localeKey);
        logger.debug("saving URL to return user to previous page");
        String urlRedirectTo = (String) request.getSession().getAttribute("currentURL");;
        Map<String, String> labels = null;
        if (isNotBlank(localeKey)){
            logger.debug("receiving labels");
            labels = labelsManager.getLabels(localeKey);
            logger.debug("labels received");
        }
        if (labels != null) {
            logger.debug("setting found labels as attribute to session");
            HttpSession session = request.getSession();
            session.setAttribute("labels",labels);
            session.setAttribute("localeKey",localeKey);
        }
        if (urlRedirectTo == null) {
            urlRedirectTo = "list";
        }
        request.setAttribute("redirectURL", urlRedirectTo);
        logger.debug("execution of Set Locale command finished");
        return "";
    }
}
