package com.javalab.contacts.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public final class SqlScriptLoader {
    private static final Logger logger = LoggerFactory.getLogger(SqlScriptLoader.class);

    private static Properties properties = PropertiesProvider.getInstance().getScriptLoaderProperties();

    public static void loadScript(String path) {
        logger.debug("Try loading script at path: {}", path);
        String[] sqlLines = new String[0];
        try (BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"))) {
            String line;
            StringBuilder fileBody = new StringBuilder();
            logger.debug("Start reading file: {}",  path);
            while ((line = bufferedReader.readLine()) != null) {
                fileBody.append(line);
            }
            sqlLines = fileBody.toString().split(";");
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
        }
        if (sqlLines.length > 0){
            logger.debug("successfully read script file {}", path);
        }
        logger.debug("trying to get connection to DB");
        Connection connection = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup(properties.getProperty("data.source"));
            connection = dataSource.getConnection();
            logger.debug("connection got {}", connection);
            logger.debug("executing script");
            Statement statement = connection.createStatement();
            for (String sqlLine : sqlLines) {
                if (!sqlLine.trim().equals("")) { //don't execute empty line
                    statement.executeUpdate(sqlLine);
                }
            }
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    logger.debug("script loaded!!");
                } catch (SQLException e) {
                    logger.error("{}",e);
                }
            }
        }
    }
}