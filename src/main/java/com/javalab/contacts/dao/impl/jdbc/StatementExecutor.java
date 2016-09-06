package com.javalab.contacts.dao.impl.jdbc;


import java.sql.SQLException;
import java.sql.Statement;

import static com.javalab.contacts.dao.impl.jdbc.DbConnectionProvider.receiveConnection;

class StatementExecutor {
    static void executeStatement(String query){
        try {
            Statement statement = receiveConnection().createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
