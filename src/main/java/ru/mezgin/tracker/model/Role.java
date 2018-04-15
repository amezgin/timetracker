package ru.mezgin.tracker.model;

/**
 * The class Role.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 13.04.2018
 */
public class Role extends AbstractBaseEntity {

    /**
     * Get by id.
     */
    public static final String GET_ROLE_BY_ID = "SELECT * FROM user_roles WHERE id=?";

    /**
     * The constructor.
     *
     * @param id   id.
     * @param name name.
     */
    public Role(Integer id, String name) {
        super(id, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Role role = (Role) o;

        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        return result;
    }

    @Override
    public String toString() {
        return "Role{"
                + "id=" + id
                + ", name=" + name
                + '}';
    }
}