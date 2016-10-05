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
    private LabelsManager labelsManager = LabelsManager.getInstance();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String localeKey = request.getParameter("localeKey");
        String urlRedirectTo = (String) request.getSession().getAttribute("currentURL");
        Map<String, String> labels = null;
        if (isNotBlank(localeKey)){
            labels = labelsManager.getLabels(localeKey);
        }
        if (labels != null) {
            HttpSession session = request.getSession();
            session.setAttribute("labels",labels);
            session.setAttribute("localeKey",localeKey);
        }
        if (urlRedirectTo == null) {
            urlRedirectTo = "list";
        }
        request.setAttribute("redirectURL", urlRedirectTo);
        return "";
    }
}
