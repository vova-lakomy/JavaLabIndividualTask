package com.javalab.contacts.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public final class LabelsManager {
    private static LabelsManager instance = new LabelsManager();
    private static final Logger logger = LoggerFactory.getLogger(LabelsManager.class);
    private Properties labelEngProperties = PropertiesProvider.getInstance().getLabelEngProperties();
    private Properties labelRusProperties = PropertiesProvider.getInstance().getLabelRusProperties();
    private Map<String,Map<String,String>> localesMap;

    public static LabelsManager getInstance() {
        return instance;
    }

    private LabelsManager() {
        initializeLocalesMap();
    }

    private void initializeLocalesMap() {
        Map<String,String> labelsEn = new HashMap<>();
        Map<String,String> labelsRu = new HashMap<>();
        for (String key : labelRusProperties.stringPropertyNames()){
            try {
                String value = new String(labelRusProperties.getProperty(key).getBytes("ISO-8859-1"),"UTF-8" );
                labelsRu.put(key,value);
            } catch (UnsupportedEncodingException e) {
                logger.error("{}",e);
            }
        }
        for (String labelName : labelEngProperties.stringPropertyNames()){
            String labelValue = labelEngProperties.getProperty(labelName);
            labelsEn.put(labelName,labelValue);
        }
        localesMap = new HashMap<>();
        localesMap.put("en",labelsEn);
        localesMap.put("ru",labelsRu);
    }

    public void setLocaleLabelsToSession(HttpSession session) {
        Object labels = session.getAttribute("labels");
        if (labels == null){
            String locale = (String) session.getAttribute("localeKey");
            if (isNotBlank(locale)){
                session.setAttribute("labels", getLabels(locale));
            } else {
                session.setAttribute("labels", getLabels());
            }
        }
    }

    public Map<String,String> getLabels(String localeKey){
        return localesMap.get(localeKey);
    }

    public Map<String,String> getLabels(){
        return localesMap.get("en");
    }
}
