package com.server.calendarapp.domain.schedule.service;

import com.server.calendarapp.domain.schedule.dto.ScheduleDto;
import com.server.calendarapp.domain.schedule.model.Schedule;
import com.server.calendarapp.domain.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public void createSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();
        schedule.setTitle(scheduleDto.getTitle());
        schedule.setAuthor(scheduleDto.getAuthor());
        schedule.setPassword(scheduleDto.getPassword());
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setUpdatedAt(LocalDateTime.now());

        schedule.setMemberId(1L);

        scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedules(String startDate, String endDate, String author) {
        return scheduleRepository.findSchedules(startDate, endDate, author);
    }
}
