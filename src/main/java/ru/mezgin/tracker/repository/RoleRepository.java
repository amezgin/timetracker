package ru.mezgin.tracker.repository;

import ru.mezgin.tracker.model.Role;

/**
 * The interface RoleRepository.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 13.04.2018
 */
public interface RoleRepository {

    /**
     * Returns the role by parameter id.
     *
     * @param id id.
     * @return role or null if not found.
     */
    Role get(int id);
}
