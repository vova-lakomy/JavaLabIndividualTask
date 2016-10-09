package com.javalab.contacts.dao.impl.jdbc;


import com.javalab.contacts.exception.ConnectionDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    private static AtomicInteger openedConnectionCount = new AtomicInteger(0);
    private static AtomicInteger totalConnectionsMade = new AtomicInteger(0);

    private static DataSource dataSource;
    private static ConnectionManager instance = new ConnectionManager();
    static ConnectionManager getInstance() {
        return instance;
    }
    private ConnectionManager() {
        try {
            logger.debug("creating instance of connection manager, getting initial context");
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/contacts_vladimir_lakomy");
        } catch (NamingException e) {
            logger.error("{}",e);
        }
    }

    public static Connection receiveConnection() throws ConnectionDeniedException {
        Connection connection;
        logger.debug("trying to get connection from pool");
        try {
            connection = dataSource.getConnection();
            logger.debug("connection from pool got.. opened connections - {} \n" +
                    "total connections made - {}",
                    openedConnectionCount.incrementAndGet(),
                    totalConnectionsMade.incrementAndGet());
        } catch (SQLException e) {
            logger.error("{}", e);
            throw new ConnectionDeniedException("can not receive connection to data base");
        }
        return connection;
    }



    static void closeResources(Connection connection, Statement statement){
        closeStatement(statement);
        closeConnection(connection);
    }

    public static void closeConnection(Connection connection) {
        if (connection != null){
            try {
                connection.close();
                logger.debug("db connection closed... opened connections - {}", openedConnectionCount.decrementAndGet());
            } catch (SQLException e) {
                logger.error("{}", e);
            }
        }
    }

    static void closeStatement(Statement statement){
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error("error {}", e);
        }
    }
}
