package com.javalab.contacts.util;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesProvider {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesProvider.class);

    private Properties connectionProperties = new Properties();
    private Properties mailProperties = new Properties();
    private Properties fileUploadProperties = new Properties();


    private static final PropertiesProvider propInstance = new PropertiesProvider();

    public static PropertiesProvider getInstance() {
        return propInstance;
    }

    private PropertiesProvider() {
        try {
            logger.debug("trying to get connection properties from configuration file");
            connectionProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sqlLoader.properties"));
        } catch (IOException e) {
            logger.error("getting connection properties failed " + e.getMessage() + " " + e.getCause());
            e.printStackTrace();
        }
        try {
            logger.debug("trying to get mail properties from configuration file");
            mailProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mail-credentials.properties"));
        } catch (IOException e) {
            logger.error("getting mail properties failed " + e.getMessage() + " " + e.getCause());
            e.printStackTrace();
        }
        try {
            logger.debug("trying to get file upload properties from configuration file");
            fileUploadProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("file-upload.properties"));
        } catch (IOException e) {
            logger.error("getting file upload properties failed " + e.getMessage() + " " + e.getCause());
            e.printStackTrace();
        }
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public Properties getMailProperties() {
        return mailProperties;
    }

    public Properties getFileUploadProperties() {
        return fileUploadProperties;
    }
}
