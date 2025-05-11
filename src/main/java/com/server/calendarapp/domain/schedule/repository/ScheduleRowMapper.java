package com.server.calendarapp.domain.schedule.repository;

import com.server.calendarapp.domain.schedule.model.Schedule;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScheduleRowMapper implements RowMapper<Schedule> {
    @Override
    public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        Schedule schedule = new Schedule();
        schedule.setId(rs.getLong("id"));
        schedule.setTitle(rs.getString("title"));
        schedule.setAuthor(rs.getString("author"));
        // schedule.setPassword(rs.getString("password"));
        schedule.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
        schedule.setUpdatedAt(rs.getTimestamp("updatedAt").toLocalDateTime());
        return schedule;
    }
}