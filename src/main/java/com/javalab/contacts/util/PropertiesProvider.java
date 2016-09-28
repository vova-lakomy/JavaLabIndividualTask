package com.javalab.contacts.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesProvider {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesProvider.class);

    private Properties scriptLoaderProperties = new Properties();
    private Properties mailProperties = new Properties();
    private Properties fileUploadProperties = new Properties();
    private Properties labelEngProperties = new Properties();
    private Properties labelRusProperties = new Properties();


    private static final PropertiesProvider propInstance = new PropertiesProvider();

    public static PropertiesProvider getInstance() {
        return propInstance;
    }

    private PropertiesProvider() {
        try {
            logger.debug("trying to get connection properties from configuration file");
            scriptLoaderProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sqlLoader.properties"));
        } catch (IOException e) {
            logger.error("getting connection properties failed {}",e);
            e.printStackTrace();
        }
        try {
            logger.debug("trying to get mail properties from configuration file");
            mailProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mail-credentials.properties"));
        } catch (IOException e) {
            logger.error("getting mail properties failed {}",e);
            e.printStackTrace();
        }
        try {
            logger.debug("trying to get file upload properties from configuration file");
            fileUploadProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("file-upload.properties"));
        } catch (IOException e) {
            logger.error("getting file upload properties failed {}",e);
            e.printStackTrace();
        }
        try {
            logger.debug("trying to get eng labels ");
            labelEngProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("labels_en.properties"));
        } catch (IOException e) {
            logger.error("getting eng labels failed {}",e);
            e.printStackTrace();
        }
        try {
            logger.debug("trying to get ru labels ");
            labelRusProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("labels_ru.properties"));
        } catch (IOException e) {
            logger.error("getting ru labels failed {}",e);
            e.printStackTrace();
        }
    }

    public Properties getScriptLoaderProperties() {
        return scriptLoaderProperties;
    }

    public Properties getMailProperties() {
        return mailProperties;
    }

    public Properties getFileUploadProperties() {
        return fileUploadProperties;
    }

    public Properties getLabelEngProperties() {
        return labelEngProperties;
    }

    public Properties getLabelRusProperties() {
        return labelRusProperties;
    }
}
