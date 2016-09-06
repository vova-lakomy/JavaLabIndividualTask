package com.javalab.contacts.dao.impl.jdbc;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

class DbWorker {

    private static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileInputStream("src/main/resources/db/mySqlConnection.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Connection receiveConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String user = properties.getProperty("mysql.user");
            String password = properties.getProperty("mysql.password");
            String options = "?useSSL=false";
            String dbURL = properties.getProperty("mysql.url") +options;
            connection = DriverManager.getConnection(dbURL,user,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    static void putBackConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void executeStatement(String query){
        try {
            Statement statement = receiveConnection().createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DbWorker() {
    }
}
