package com.server.calendarapp.domain.schedule.repository;

import com.server.calendarapp.domain.schedule.model.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Schedule schedule) {
        String sql = "INSERT INTO schedule (memberId, title, author, password, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                schedule.getMemberId(),
                schedule.getTitle(),
                schedule.getAuthor(),
                schedule.getPassword(),
                Timestamp.valueOf(schedule.getCreatedAt()),
                Timestamp.valueOf(schedule.getUpdatedAt())
        );
    }
}
