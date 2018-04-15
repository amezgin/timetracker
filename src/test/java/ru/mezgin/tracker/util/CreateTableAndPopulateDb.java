package ru.mezgin.tracker.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The class AuthFilterTest.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 15.04.2018
 */
public class CreateTableAndPopulateDb {

    /**
     * The connection.
     */
    private Connection conn;

    /**
     * The Constructor.
     *
     * @param conn connection.
     */
    public CreateTableAndPopulateDb(Connection conn) {
        this.conn = conn;
        createTable();
    }

    /**
     * This method create the table in the database.
     */
    private void createTable() {
        try (Statement st = this.conn.createStatement()) {
            st.execute("DROP TABLE IF EXISTS statuses;");
            st.execute("DROP TABLE IF EXISTS users;");
            st.execute("DROP TABLE IF EXISTS user_roles;");
            st.execute("DROP SEQUENCE IF EXISTS global_seq;");
            st.execute("CREATE SEQUENCE global_seq START 100000;");
            st.execute("CREATE TABLE user_roles (id INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),"
                    + " role_name VARCHAR(100) NOT NULL UNIQUE);");
            st.execute("CREATE TABLE users (id  INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),"
                    + " name VARCHAR NOT NULL, password VARCHAR  NOT NULL, role_id INTEGER NOT NULL,"
                    + " enabled  BOOL DEFAULT FALSE  NOT NULL,"
                    + " FOREIGN KEY (role_id) REFERENCES user_roles (id) ON UPDATE CASCADE ON DELETE CASCADE);");
            st.execute("CREATE TABLE statuses (id INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),"
                    + " name VARCHAR NOT NULL, date_time TIMESTAMP NOT NULL, user_id INTEGER NOT NULL,"
                    + " start_new_work_day BOOL NOT NULL, end_work_day BOOL NOT NULL,"
                    + " FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE);");
            st.execute("CREATE UNIQUE INDEX status_unique_user_datetime_idx ON statuses (user_id, date_time);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method populate the db.
     */
    public void populateDB() {
        try (Statement st = this.conn.createStatement()) {
            st.execute("DELETE FROM user_roles;");
            st.execute("DELETE FROM users;");
            st.execute("DELETE FROM statuses;");
            st.execute("ALTER SEQUENCE global_seq RESTART WITH 100000;");
            st.execute("INSERT INTO user_roles (role_name) VALUES ('USER'), ('ADMIN');");
            st.execute("INSERT INTO users (name, password, role_id, enabled) VALUES"
                    + "('user', 'user', '100000', 'true'), ('root', 'root', '100001', 'true');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
