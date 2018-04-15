package ru.mezgin.tracker.repository;

import ru.mezgin.tracker.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The class JdbcRoleRepositoryImpl.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 13.04.2018
 */
public class JdbcRoleRepositoryImpl implements RoleRepository {

    /**
     * The connection.
     */
    private Connection conn;

    /**
     * The constructor.
     *
     * @param conn connection.
     */
    public JdbcRoleRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Role get(int id) {
        Role result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(Role.GET_ROLE_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result = new Role(null, rs.getString("role_name"));
                    result.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
