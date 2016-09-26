package com.javalab.contacts.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public final class SqlScriptLoader {
    private static final Logger logger = LoggerFactory.getLogger(SqlScriptLoader.class);

    private static Properties properties = PropertiesProvider.getInstance().getConnectionProperties();

    public static void loadScript(String path) {
        logger.debug("Try loading script at path: " + path);
        String[] sqlLines = new String[0];
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)))) {
            String line;
            StringBuilder fileBody = new StringBuilder();
            logger.debug("Start reading file: " + path);
            while ((line = bufferedReader.readLine()) != null) {
                fileBody.append(line);
            }
            sqlLines = fileBody.toString().split(";");
        } catch (Exception e) {
            logger.error("\n                  " +  e);
        }
        if (sqlLines.length > 0){
            logger.debug("successfully read script file " + path);
        }
        logger.debug("trying to get connection to DB");
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String user = properties.getProperty("mysql.user");
            String password = properties.getProperty("mysql.password");
            String options = "?useSSL=false&characterEncoding=UTF-8";
            String dbURL = properties.getProperty("mysql.schema.url") + options;

            connection = DriverManager.getConnection(dbURL, user, password);
            logger.debug("connection got " + connection);
            logger.debug("executing script");
            Statement statement = connection.createStatement();
            for (String sqlLine : sqlLines) {
                if (!sqlLine.trim().equals("")) { //don't execute empty line
                    statement.executeUpdate(sqlLine);
                }
            }
        } catch (Exception e) {
            logger.error("\n                  " +  e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    logger.debug("script loaded!!");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}