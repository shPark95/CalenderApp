package com.server.calendarapp.domain.schedule.service;

import com.server.calendarapp.common.exception.InvalidPasswordException;
import com.server.calendarapp.common.exception.ScheduleNotFoundException;
import com.server.calendarapp.domain.schedule.dto.ScheduleDto;
import com.server.calendarapp.domain.schedule.model.Schedule;
import com.server.calendarapp.domain.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 일정 생성
    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    // 일정 목록 조회 (날짜, 작성자)
    public List<ScheduleDto> getSchedules(String startDate, String endDate, Long memberId) {
        List<Schedule> schedules = scheduleRepository.findSchedules(startDate, endDate, memberId);
        return schedules.stream()
                .map(ScheduleDto::new)
                .collect(Collectors.toList());
    }

    // 페이지네이션 조회
    public List<ScheduleDto> getPagedSchedules(int page, int size) {
        int offset = (page - 1) * size;
        List<Schedule> schedules = scheduleRepository.findPagedSchedules(size, offset);
        return schedules.stream()
                .map(ScheduleDto::new)
                .collect(Collectors.toList());
    }

    // 선택한 일정ID로 조회
    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    // 선택한 일정 수정
    public Schedule updateSchedule(Long id, Schedule schedule) {
        Schedule updated = scheduleRepository.findById(id);

        if (!updated.getPassword().equals(schedule.getPassword())) {
            throw new InvalidPasswordException();
        }

        updated.setTitle(schedule.getTitle());
        updated.setAuthor(schedule.getAuthor());
        updated.setUpdatedAt(LocalDateTime.now());

        return scheduleRepository.updateSchedule(updated);
    }

    // 선택한 일정 삭제
    public boolean deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findById(id);
        if (!schedule.getPassword().equals(password)) {
            return false;
        }

        scheduleRepository.deleteSchedule(id);
        return true;
    }
}
