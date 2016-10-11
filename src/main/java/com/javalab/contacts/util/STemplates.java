package com.javalab.contacts.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public final class STemplates {

    private static final Logger logger = LoggerFactory.getLogger(STemplates.class);
    private STGroup templates;
    private AtomicInteger templateCounter = new AtomicInteger(1);
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

    public int getAndIncrementTemplateCounter() {
        return templateCounter.incrementAndGet();
    }
}
