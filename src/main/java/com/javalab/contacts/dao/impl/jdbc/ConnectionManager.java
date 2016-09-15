package com.javalab.contacts.dao.impl.jdbc;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

class ConnectionManager {

    private int openedConnectionCount;
    private int totalConnectionsMade;


    private DataSource dataSource;
    private static final Logger logger = LogManager.getLogger(ConnectionManager.class);

    private static ConnectionManager instance = new ConnectionManager();

    static ConnectionManager getInstance() {
        return instance;
    }

    private ConnectionManager() {
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/contacts_vladimir_lakomy");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    Connection receiveConnection() {
        Connection connection = null;
        logger.debug("trying to get connection from pool");
        try {
            connection = dataSource.getConnection();
            logger.debug("connection from pool got.. opened connections - " + ++openedConnectionCount);
            logger.debug("total connections made - " + ++totalConnectionsMade);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    void putBackConnection(Connection connection) {
        try {
            connection.close();
            logger.debug("db connection closed... opened connections - " + --openedConnectionCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
