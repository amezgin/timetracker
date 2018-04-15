package ru.mezgin.tracker.service;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The class ConnectionFactoryImpl.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 15.04.2018
 */
public enum ConnectionFactoryImpl implements ConnectionFactory {

    /**
     * The instance.
     */
    INSTANCE;

    /**
     * The connection for the database.
     */
    private Connection conn = null;

    /**
     * The BasicDataSource.
     */
    private BasicDataSource ds = null;

    /**
     * The constructor.
     */
    ConnectionFactoryImpl() {
        this.ds = new BasicDataSource();
        this.ds.setDriverClassName("org.postgresql.Driver");
        this.ds.setUrl("jdbc:postgresql://localhost:5432/testtracker");
        this.ds.setUsername("user");
        this.ds.setPassword("password");
        this.ds.setMinIdle(5);
        this.ds.setMaxIdle(25);
        this.ds.setMaxOpenPreparedStatements(180);
        try {
            this.conn = this.ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        return this.conn;
    }

    @Override
    public void closeConnectionsPool() {
        if (!this.ds.isClosed()) {
            try {
                this.ds.close();
                this.ds = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}