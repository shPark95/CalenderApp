package com.server.calendarapp.domain.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.server.calendarapp.domain.schedule.model.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleDto {
    private final Long id;
    private final Long memberId;
    private final String title;
    private final String author;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime updatedAt;

    public ScheduleDto(Schedule schedule) {
        this.id = schedule.getId();
        this.memberId = schedule.getMemberId();
        this.title = schedule.getTitle();
        this.author = schedule.getAuthor();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
