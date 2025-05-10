package com.server.calendarapp.domain.member.repository;

import com.server.calendarapp.domain.member.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createMember(Member member) {
        String sql = "INSERT INTO member (name, email, createdAt, updatedAt) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, member.getName(), member.getEmail(), member.getCreatedAt(), member.getUpdatedAt());
    }

}