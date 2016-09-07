package com.javalab.contacts.dao.impl.jdbc;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionProvider {
    private static final Logger logger = LogManager.getLogger(DbConnectionProvider.class);


    private static int connectionCount;

    private static Properties properties = new Properties();
    static {
        try {
            logger.debug("trying to get connection properties");
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("mySqlConnection.properties"));
        } catch (IOException e) {
            logger.error("getting connection properties failed " + e.getMessage() + " " + e.getCause());
            e.printStackTrace();
        }
    }

    public static Connection receiveConnection(){
        logger.debug("trying to get connection to DB");
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String user = properties.getProperty("mysql.user");
            String password = properties.getProperty("mysql.password");
            String options = "?useSSL=false";
            String dbURL = properties.getProperty("mysql.url") + options;
            connection = DriverManager.getConnection(dbURL,user,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("connection got.. connection number - " + ++connectionCount);
        return connection;
    }

    static void putBackConnection(Connection connection){
        try {
            connection.close();
            logger.debug("connection closed " + --connectionCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DbConnectionProvider() {
    }
}
