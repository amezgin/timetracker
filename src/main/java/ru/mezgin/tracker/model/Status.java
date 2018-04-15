package ru.mezgin.tracker.model;

import java.time.LocalDateTime;

/**
 * The class Status. This class describes a user action.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 13.04.2018
 */
public class Status extends AbstractBaseEntity {

    /**
     * Create.
     */
    public static final String CREATE_STATUS = "INSERT INTO statuses (name, date_time, user_id, start_new_work_day, end_work_day) "
            + "VALUES (?, ?, ?, ?, ?)";

    /**
     * Get last start work day.
     */
    public static final String GET_LAST_START_WORK_DAY = "SELECT * FROM statuses WHERE user_id=? AND start_new_work_day='true' "
            + "ORDER BY date_time DESC LIMIT 1";

    /**
     * Get last end work day.
     */
    public static final String GET_LAST_END_WORK_DAY = "SELECT * FROM statuses WHERE user_id=? AND end_work_day='true' "
            + "ORDER BY date_time DESC LIMIT 1";

    /**
     * Get last action user.
     */
    public static final String GET_LAST_ACTION_USER = "SELECT * FROM statuses WHERE user_id=? "
            + "ORDER BY date_time DESC LIMIT 1";

    /**
     * Get status between two dates.
     */
    public static final String GET_STATUS_BETWEEN_TIMES = "SELECT * FROM statuses WHERE user_id=? "
            + "AND date_time BETWEEN ? AND ? ORDER BY date_time DESC";

    /**
     * The creation date of the status.
     */
    private LocalDateTime dateTime;

    /**
     * If this is a new working day, it is set to true.
     */
    private boolean startNewWorkDay = false;

    /**
     * If this is an end working day, it is set to true.
     */
    private boolean endWorkDay = false;

    /**
     * The constructor.
     *
     * @param id              id.
     * @param name            name.
     * @param dateTime        date of create.
     * @param startNewWorkDay if it is a new working day, it is set to true otherwise false.
     * @param endWorkDay      if it is an end working day, it is set to true otherwise false.
     */
    public Status(Integer id, String name, LocalDateTime dateTime, boolean startNewWorkDay, boolean endWorkDay) {
        super(id, name);
        this.dateTime = dateTime;
        this.startNewWorkDay = startNewWorkDay;
        this.endWorkDay = endWorkDay;
    }

    /**
     * Get the creation date of the status.
     *
     * @return the creation date of the status.
     */
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * Set the creation date of the status.
     *
     * @param dateTime the creation date of the status.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Verifies that it is the new work day.
     *
     * @return true if that is the new work day otherwise false.
     */
    public boolean isStartNewWorkDay() {
        return startNewWorkDay;
    }

    /**
     * Sets true if that is the new work day.
     *
     * @param startNewWorkDay true if that is the new work day.
     */
    public void setStartNewWorkDay(boolean startNewWorkDay) {
        this.startNewWorkDay = startNewWorkDay;
    }

    /**
     * Verifies that it is the end work day.
     *
     * @return true if that is the end work day otherwise false.
     */
    public boolean isEndWorkDay() {
        return endWorkDay;
    }

    /**
     * Sets true if that is the new work day.
     *
     * @param endWorkDay true if that is the end work day.
     */
    public void setEndWorkDay(boolean endWorkDay) {
        this.endWorkDay = endWorkDay;
    }

    @Override
    public String toString() {
        return "Status{"
                + ", id=" + id
                + ", name='" + name
                + "dateTime=" + dateTime
                + '}';
    }
}
