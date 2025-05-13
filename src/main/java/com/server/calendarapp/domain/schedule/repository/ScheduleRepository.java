package com.server.calendarapp.domain.schedule.repository;

import com.server.calendarapp.domain.schedule.model.Schedule;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Schedule> findSchedules(String startDate, String endDate, Long memberId) {
        StringBuilder sql = new StringBuilder(
            "SELECT s.* FROM schedule s JOIN member m ON s.memberId = m.id WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();

        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            sql.append(" AND DATE(s.updatedAt) BETWEEN ? AND ?");
            params.add(LocalDate.parse(startDate));
            params.add(LocalDate.parse(endDate));
        } else if (startDate != null && !startDate.isEmpty()) {
            sql.append(" AND DATE(s.updatedAt) >= ?");
            params.add(LocalDate.parse(startDate));
        } else if (endDate != null && !endDate.isEmpty()) {
            sql.append(" AND DATE(s.updatedAt) <= ?");
            params.add(LocalDate.parse(endDate));
        }

        // 작성자명 조건 추가
        if (memberId != null) {
            sql.append(" AND s.memberId = ?");
            params.add(memberId);
        }

        // 내림차순 정렬
        sql.append(" ORDER BY s.updatedAt DESC");

        return jdbcTemplate.query(sql.toString(), params.toArray(), new ScheduleRowMapper());
    }

    public List<Schedule> findPagedSchedules(int limit, int offset) {
        String sql = """
            SELECT s.id, s.memberId, s.title, m.name AS author, s.password, s.createdAt, s.updatedAt
            FROM schedule s
            JOIN member m ON s.memberId = m.id
            ORDER BY s.updatedAt DESC
            LIMIT ? OFFSET ?
        """;

        return jdbcTemplate.query(sql, new Object[]{limit, offset}, new ScheduleRowMapper());
    }

    // 선택한 일정ID로 조회
    public Optional<Schedule> findById(long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, new Object[]{id}, new ScheduleRowMapper());
        return result.stream().findFirst();
    }

    public void updateSchedule(Schedule schedule) {
        String sql = "UPDATE schedule SET title = ?, author = ?, updatedAt = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                schedule.getTitle(),
                schedule.getAuthor(),
                schedule.getUpdatedAt(),
                schedule.getId());
    }

    public void deleteSchedule(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}
