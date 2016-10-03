package com.javalab.contacts.dao.impl.jdbc;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

class ConnectionManager {

    private AtomicInteger openedConnectionCount = new AtomicInteger(0);
    private AtomicInteger totalConnectionsMade = new AtomicInteger(0);

    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    private static ConnectionManager instance = new ConnectionManager();

    static ConnectionManager getInstance() {
        return instance;
    }

    private DataSource dataSource;

    private ConnectionManager() {
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/contacts_vladimir_lakomy");
        } catch (NamingException e) {
            logger.error("{}",e);
        }
    }

    Connection receiveConnection() {
        Connection connection = null;
        logger.debug("trying to get connection from pool");
        try {
            connection = dataSource.getConnection();
            logger.debug("connection from pool got.. opened connections - {}", openedConnectionCount.incrementAndGet());
            logger.debug("total connections made - {}", totalConnectionsMade.incrementAndGet());
        } catch (SQLException e) {
            logger.error("{}",e);
        }
        return connection;
    }

    void putBackConnection(Connection connection) {
        try {
            connection.close();
            logger.debug("db connection closed... opened connections - {}", openedConnectionCount.decrementAndGet());
        } catch (SQLException e) {
            logger.error("{}",e);
        }
    }
}
