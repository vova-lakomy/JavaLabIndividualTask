package com.javalab.contacts.service.command;

import com.javalab.contacts.util.PropertiesProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class SetLocaleCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(SetLocaleCommand.class);
    private Properties labelEngProperties = PropertiesProvider.getInstance().getLabelEngProperties();
    private Properties labelRusProperties = PropertiesProvider.getInstance().getLabelRusProperties();
    private Map<String,String> labels;
    private Map<String,Map<String,String>> locales;
    public SetLocaleCommand(){
        initializeLocalesMap();
        labels = locales.get("en");
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String localeKey = request.getParameter("localeKey");
        String redirectUrl = (String) request.getSession().getAttribute("currentURL");
        if (isNotBlank(localeKey)){
            labels = locales.get(localeKey);
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

    private void initializeLocalesMap() {
        Map<String,String> labelsEn = new HashMap<>();
        Map<String,String> labelsRu = new HashMap<>();
        for (String key : labelRusProperties.stringPropertyNames()){
            try {
                String value = new String(labelRusProperties.getProperty(key).getBytes("ISO-8859-1"),"UTF-8" );
                labelsRu.put(key,value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        for (String labelName : labelEngProperties.stringPropertyNames()){
            String labelValue = labelEngProperties.getProperty(labelName);
            labelsEn.put(labelName,labelValue);
        }
        locales = new HashMap<>();
        locales.put("en",labelsEn);
        locales.put("ru",labelsRu);
    }

}
