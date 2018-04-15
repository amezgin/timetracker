package ru.mezgin.tracker.repository;

import ru.mezgin.tracker.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The class JdbcStatusRepositoryImpl.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 13.04.2018
 */
public class JdbcStatusRepositoryImpl implements StatusRepository {

    /**
     * The connection.
     */
    private Connection conn;

    /**
     * The constructor.
     *
     * @param conn the connection.
     */
    public JdbcStatusRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Status save(Status status, int userId) {
        Integer id = null;
        try (PreparedStatement ps = this.conn.prepareStatement(Status.CREATE_STATUS)) {
            ps.setString(1, status.getName());
            ps.setObject(2, status.getDateTime());
            ps.setInt(3, userId);
            ps.setBoolean(4, status.isStartNewWorkDay());
            ps.setBoolean(5, status.isEndWorkDay());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            status.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public Status getLastStatusOnCondition(String sqlQuery, int userId) {
        Status result = null;
        try (PreparedStatement ps = this.conn.prepareStatement(sqlQuery)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result = new Status(rs.getInt("id"), rs.getString("name"),
                            rs.getTimestamp("date_time").toLocalDateTime(),
                            rs.getBoolean("start_new_work_day"), rs.getBoolean("end_work_day"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Status> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<Status> result = new ArrayList<>();
        try (PreparedStatement ps = this.conn.prepareStatement(Status.GET_STATUS_BETWEEN_TIMES)) {
            ps.setInt(1, userId);
            ps.setObject(2, startDate);
            ps.setObject(3, endDate);
            Status status = null;
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    status = new Status(rs.getInt("id"), rs.getString("name"),
                            rs.getTimestamp("date_time").toLocalDateTime(),
                            rs.getBoolean("start_new_work_day"), rs.getBoolean("end_work_day"));
                    result.add(status);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
