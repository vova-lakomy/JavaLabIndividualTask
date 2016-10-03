package com.javalab.contacts.service.command;

import com.javalab.contacts.util.LabelsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class SetLocaleCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(SetLocaleCommand.class);
    LabelsManager labelsManager = LabelsManager.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String localeKey = request.getParameter("localeKey");
        String redirectUrl = (String) request.getSession().getAttribute("currentURL");
        Map<String, String> labels = null;
        if (isNotBlank(localeKey)){
            labels = labelsManager.getLabels(localeKey);
        }
        if (labels != null) {
            HttpSession session = request.getSession();
            session.setAttribute("labels",labels);
            session.setAttribute("localeKey",localeKey);
        }
        if (redirectUrl == null) {
            redirectUrl = "list";
        }
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            logger.error("{}",e);
        }
    }
}
