package ru.mezgin.tracker.service;

import java.sql.Connection;

/**
 * The interface ConnectionFactory.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 15.04.2018
 */
public interface ConnectionFactory {

    /**
     * Returns the connection to database.
     *
     * @return connection.
     */
    Connection getConnection();

    /**
     * Close pool connection.
     */
    void closeConnectionsPool();
}