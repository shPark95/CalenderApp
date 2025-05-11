package com.server.calendarapp.domain.schedule.repository;

import com.server.calendarapp.domain.schedule.model.Schedule;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
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

    public List<Schedule> findSchedules(String startDate, String endDate, String author) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
            sql.append(" AND DATE(updatedAt) BETWEEN ? AND ?");
            params.add(LocalDate.parse(startDate));
            params.add(LocalDate.parse(endDate));
        } else if (startDate != null && !startDate.isEmpty()) {
            sql.append(" AND DATE(updatedAt) >= ?");
            params.add(LocalDate.parse(startDate));
        } else if (endDate != null && !endDate.isEmpty()) {
            sql.append(" AND DATE(updatedAt) <= ?");
            params.add(LocalDate.parse(endDate));
        }

        // 작성자명 조건 추가
        if (author != null && !author.isEmpty()) {
            sql.append(" AND author = ?");
            params.add(author);
        }

        // 내림차순 정렬
        sql.append(" ORDER BY updatedAt DESC");

        return jdbcTemplate.query(sql.toString(), params.toArray(), new ScheduleRowMapper());
    }

    // 선택한 일정ID로 조회
    public Schedule findById(long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{id},
                    new ScheduleRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("일정을 찾을 수 없습니다.");
        }
    }
}
