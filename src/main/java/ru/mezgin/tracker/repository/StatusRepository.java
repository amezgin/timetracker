package ru.mezgin.tracker.repository;

import ru.mezgin.tracker.model.Status;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface StatusRepository.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 13.04.2018
 */
public interface StatusRepository {

    /**
     * Save or update the user status in the storage.
     *
     * @param status user Status.
     * @param userId user ID.
     * @return the status of the user if it is saved or updated in the vault otherwise false.
     */
    Status save(Status status, int userId);

    /**
     * Returns the last user actions from storage be sql query.
     *
     * @param sqlQuery sql query.
     * @param userId   user ID.
     * @return tha last user actions from storage by sql query. If action not found than return false.
     */
    Status getLastStatusOnCondition(String sqlQuery, int userId);

    /**
     * Returns a list of recent user actions between specific dates. The startDate  and the endDate must not be null.
     * The list is ordered date time by desc.
     *
     * @param startDate the date of start period.
     * @param endDate   the date of end period.
     * @param userId    the user ID.
     * @return a list of recent user actions.
     */
    List<Status> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);
}
