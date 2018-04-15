package ru.mezgin.tracker.model;

/**
 * The class User.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 13.04.2018
 */
public class User extends AbstractBaseEntity {

    /**
     * Create the user.
     */
    public static final String CREATE_USER = "INSERT INTO users (name, password, role_id, enabled) VALUES (?, ?, ?, ?)";

    /**
     * Update the user.
     */
    public static final String UPDATE_USER = "UPDATE users SET name=?, password=?, role_id=?, enabled=? WHERE id=?";

    /**
     * Delete the user.
     */
    public static final String DELETE_USER = "DELETE FROM users WHERE id=?";

    /**
     * Get the user by id.
     */
    public static final String GET_USER_BY_ID = "SELECT u.id, u.name, u.password, u.role_id, u.enabled, r.role_name FROM users AS u "
            + "INNER JOIN user_roles AS r ON u.role_id=r.id WHERE u.id=?";

    /**
     * Get the user by name.
     */
    public static final String GET_USER_BY_NAME = "SELECT u.id, u.name, u.password, u.role_id, u.enabled, r.role_name FROM users AS u "
            + "INNER JOIN user_roles AS r ON u.role_id=r.id WHERE u.name = ?";

    /**
     * Get all users.
     */
    public static final String GET_ALL_USERS = "SELECT u.id, u.name, u.password, u.role_id, u.enabled, r.role_name FROM users AS u "
            + "INNER JOIN user_roles AS r ON u.role_id=r.id";

    /**
     * Password.
     */
    private String password;

    /**
     * Role.
     */
    private Role role;

    /**
     * If the user is active, that setup true.
     */
    private boolean enabled = true;

    /**
     * The constructor.
     *
     * @param id       id.
     * @param name     name.
     * @param password password.
     * @param role     role.
     * @param enabled  enabled.
     */
    public User(Integer id, String name, String password, Role role, boolean enabled) {
        super(id, name);
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    /**
     * Get the password.
     *
     * @return password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password.
     *
     * @param password password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the role.
     *
     * @return role.
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * Set the role.
     *
     * @param role role.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * If the user is active, that setup true otherwise false.
     *
     * @return status the user.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set the user status.
     *
     * @param enabled If the user is active, that setup true otherwise false.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!name.equals(user.name)) {
            return false;
        }
        if (!password.equals(user.password)) {
            return false;
        }
        return enabled == user.enabled;
    }

    @Override
    public int hashCode() {
        int result = id;
        return result;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name=" + name
                + ", password=" + password
                + ", enabled=" + enabled
                + '}';
    }
}
