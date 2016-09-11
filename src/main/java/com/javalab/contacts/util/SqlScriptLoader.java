package com.javalab.contacts.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

import static com.javalab.contacts.dao.impl.jdbc.DbConnectionProvider.receiveConnection;
import static com.javalab.contacts.dao.impl.jdbc.DbConnectionProvider.receiveInitialConnection;

public class SqlScriptLoader {
    private static final Logger logger = LogManager.getLogger(SqlScriptLoader.class);


    public static void loadScript(String path) {
        logger.debug("loading script at path:" + path);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)))) {
            String line;
            StringBuilder fileBody = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                fileBody.append(line);
            }
            String[] sqlLines = fileBody.toString().split(";");
            try (Connection connection = receiveInitialConnection()) {
                Statement statement = connection.createStatement();
                for (String sqlLine : sqlLines) {
                    if (!sqlLine.trim().equals("")) { //don't execute empty line
                        statement.executeUpdate(sqlLine);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        logger.debug("script loaded");
    }

}