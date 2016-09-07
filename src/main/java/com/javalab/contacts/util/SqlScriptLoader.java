package com.javalab.contacts.util;


import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

public class SqlScriptLoader {
    private static Properties props = new Properties();
    static {
        try {
            props.load(new FileInputStream("/home/vv/DATA/JavaLabIndividualTask/JavaLabIndividualTask/target/contacts-list-1.0-SNAPSHOT/WEB-INF/classes/db/mySqlConnection.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadScript(String path){


        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(path)))){
            String line;
            StringBuilder fileBody = new StringBuilder();
            while((line = bufferedReader.readLine()) != null){
                fileBody.append(line);
            }
            String[] sqlLines = fileBody.toString().split(";");
            try(Connection connection = receiveConnection()){
                Statement statement = connection.createStatement();
                for (String sqlLine : sqlLines) {
                    if (!sqlLine.trim().equals("")) { //don't execute empty line
                        statement.executeUpdate(sqlLine);
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private static Connection receiveConnection(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(props.getProperty("mysql.url") + "?useSSL=false",
                                                    props.getProperty("mysql.user"),props.getProperty("mysql.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

}
