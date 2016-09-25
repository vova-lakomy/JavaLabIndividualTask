package com.javalab.contacts.util;


import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class STemplates {

    private STGroup templates;
    private static STemplates instance = new STemplates();
    public static STemplates getInstance(){
        return instance;
    }

    private STemplates() {
        URL templatesUrl = Thread.currentThread().getContextClassLoader().getResource("templates.stg");
        String templatePath;
        if (templatesUrl != null) {
            templatePath = templatesUrl.getPath();
            templates = new STGroupFile(templatePath);
        }
    }

    public Map<String,String> getTemplateMap(){
        Map<String,String> resultMap = new HashMap<>();
        Set<String> templateNames = instance.templates.getTemplateNames();
        templateNames.forEach(templateName -> {
            templateName = templateName.replace("/","");
            String templateSource = this.templates.lookupTemplate(templateName).getTemplateSource();
            resultMap.put(templateName, templateSource);
        });
        return resultMap;
    }

    public STGroup getTemplatesGroup() {
        return templates;
    }
}
