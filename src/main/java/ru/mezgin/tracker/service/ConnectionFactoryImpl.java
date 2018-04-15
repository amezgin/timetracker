package ru.mezgin.tracker.service;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The class ConnectionFactoryImpl.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 13.04.2018
 */
public enum ConnectionFactoryImpl implements ConnectionFactory {

    /**
     * The instance.
     */
    INSTANCE;

    /**
     * Contains the name of the properties file.
     */
    private static final String PATH_TO_PROPERTIES = "postgres.properties";

    /**
     * The connection for the database.
     */
    private Connection conn = null;

    /**
     * The properties.
     */
    private Properties prs = new Properties();

    /**
     * The BasicDataSource.
     */
    private BasicDataSource ds = null;

    /**
     * The constructor.
     */
    ConnectionFactoryImpl() {
        loadProperties();
        this.ds = new BasicDataSource();
        this.ds.setDriverClassName(this.prs.getProperty("driver_class"));
        this.ds.setUrl(this.prs.getProperty("url"));
        this.ds.setUsername(this.prs.getProperty("username"));
        this.ds.setPassword(this.prs.getProperty("password"));
        this.ds.setMinIdle(5);
        this.ds.setMaxIdle(25);
        this.ds.setMaxOpenPreparedStatements(180);
        try {
            this.conn = this.ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the properties.
     */
    private void loadProperties() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(PATH_TO_PROPERTIES)) {
            this.prs.load(in);
        } catch (IOException e1) {
            e1.printStackTrace();
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