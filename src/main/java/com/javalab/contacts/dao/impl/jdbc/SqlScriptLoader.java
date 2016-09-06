package com.javalab.contacts.dao.impl.jdbc;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

import static com.javalab.contacts.dao.impl.jdbc.DbConnectionProvider.receiveConnection;

class SqlScriptLoader {

    static void loadScript(String path)
    {
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

}
