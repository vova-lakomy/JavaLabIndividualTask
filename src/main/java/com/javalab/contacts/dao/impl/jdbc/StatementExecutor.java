package com.javalab.contacts.dao.impl.jdbc;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.javalab.contacts.dao.impl.jdbc.DbConnectionProvider.receiveConnection;

class StatementExecutor {
    static Integer executeStatement(String query){
        Integer lastGeneratedValue=null;
        try {
            Statement statement = receiveConnection().createStatement();
            statement.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                lastGeneratedValue = resultSet.getInt(1);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastGeneratedValue;
    }
}
