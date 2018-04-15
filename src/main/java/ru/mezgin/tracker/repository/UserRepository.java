package ru.mezgin.tracker.repository;

import ru.mezgin.tracker.model.User;

import java.util.List;

/**
 * The interface UserRepository.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 13.04.2018
 */
public interface UserRepository {

    /**
     * Save or update user on the storage.
     *
     * @param user user.
     * @return user if it will be saved or updated on storage. Otherwise returns null.
     */
    User save(User user);

    /**
     * Delete user from the storage.
     *
     * @param id user ID.
     * @return true if user will be deleted. Otherwise returns null.
     */
    boolean delete(int id);

    /**
     * Returns the user by parameter id.
     *
     * @param id user Id.
     * @return user or null if not found.
     */
    User get(int id);

    /**
     * Returns the user by parameter login.
     *
     * @param login user login.
     * @return user or null if not found.
     */
    User getByLogin(String login);

    /**
     * Returns the list of all users.
     *
     * @return the list of all users. If users will be not found than returns null.
     */
    List<User> getAll();
}