package ru.mezgin.tracker.repository;

import ru.mezgin.tracker.model.Role;
import ru.mezgin.tracker.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class JdbcUserRepositoryImpl.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 13.04.2018
 */
public class JdbcUserRepositoryImpl implements UserRepository {

    /**
     * The connection.
     */
    private Connection conn;

    /**
     * The constructor.
     *
     * @param conn connection.
     */
    public JdbcUserRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public User save(User user) {
        if (user.isNew() || user.getId() == 0) {
            Integer userId = -1;
            try (PreparedStatement ps = this.conn.prepareStatement(User.CREATE_USER, new String[]{"id"})) {
                ps.setString(1, user.getName());
                ps.setString(2, user.getPassword());
                ps.setInt(3, user.getRole().getId());
                ps.setBoolean(4, user.isEnabled());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    userId = rs.getInt("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            user.setId(userId);
        } else {
            try (PreparedStatement ps = this.conn.prepareStatement(User.UPDATE_USER)) {
                ps.setString(1, user.getName());
                ps.setString(2, user.getPassword());
                ps.setInt(3, user.getRole().getId());
                ps.setBoolean(4, user.isEnabled());
                ps.setInt(5, user.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (PreparedStatement ps = this.conn.prepareStatement(User.DELETE_USER)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User get(int id) {
        User result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(User.GET_USER_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result = new User(null, rs.getString("name"), rs.getString("password"),
                            new Role(rs.getInt("role_id"), rs.getString("role_name")), rs.getBoolean("enabled"));
                    result.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User getByLogin(String login) {
        User result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(User.GET_USER_BY_NAME)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result = new User(null, rs.getString("name"), rs.getString("password"),
                            new Role(rs.getInt("role_id"), rs.getString("role_name")), rs.getBoolean("enabled"));
                    result.setId(rs.getInt("id"));
                    result.getRole().setId(rs.getInt("role_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        try (PreparedStatement ps = this.conn.prepareStatement(User.GET_ALL_USERS)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User(null, rs.getString("name"), rs.getString("password"),
                            new Role(rs.getInt("role_id"), rs.getString("role_name")), rs.getBoolean("enabled"));
                    user.setId(rs.getInt("id"));
                    result.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Verifies the correctness of the user data.
     *
     * @param login    the user login.
     * @param password the user password.
     * @return true if data is correct. Otherwise returns false.
     */
    public boolean isCredentional(String login, String password) {
        boolean exists = false;
        for (User user : this.getAll()) {
            if (user.getName().equals(login) && user.getPassword().equals(password) && user.isEnabled()) {
                exists = true;
                break;
            }
        }
        return exists;
    }
}
