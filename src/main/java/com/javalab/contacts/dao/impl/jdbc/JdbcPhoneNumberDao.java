package com.javalab.contacts.dao.impl.jdbc;

import com.javalab.contacts.dao.PhoneNumberDAO;
import com.javalab.contacts.model.PhoneNumber;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class JdbcPhoneNumberDao implements PhoneNumberDAO {
    Properties properties = new Properties();
    {
        try {
            properties.load(new FileInputStream("src/main/resources/db/mySqlConnection.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Connection connection = null;

    public Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String sqlConnectQuery =
                    properties.getProperty("mysql.url") +
                    "?user=" + properties.getProperty("mysql.user") +
                    "&password=" + properties.getProperty("mysql.password") +
                    "&useSSL=";
            if(connection == null)
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/contacts?user=root&password=root&useSSL=false");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public PhoneNumber get(Integer id) {
        return null;
    }

    public Integer save(PhoneNumber phoneNumber) {
        return null;
    }

    public Boolean delete(int id) {
        return null;
    }

    public static void main(String[] args) {
        System.out.println(new JdbcPhoneNumberDao().getConnection());
    }
}
