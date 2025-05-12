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

    // 일정 생성
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

    // 일정 목록 조회 (날짜, 작성자)
    public List<Schedule> getSchedules(String startDate, String endDate, Long memberId) {
        return scheduleRepository.findSchedules(startDate, endDate, memberId);
    }

    // 페이지네이션 조회
    public List<Schedule> getPagedSchedules(int page, int size) {
        int offset = (page - 1) * size;
        return scheduleRepository.findPagedSchedules(size, offset);
    }

    // 선택한 일정ID로 조회
    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    // 선택한 일정 수정
    public void updateSchedule(Long id, ScheduleDto dto) {
        Schedule schedule = scheduleRepository.findById(id);

        if (!schedule.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        schedule.setTitle(dto.getTitle());
        schedule.setAuthor(dto.getAuthor());
        schedule.setUpdatedAt(LocalDateTime.now());

        scheduleRepository.updateSchedule(schedule);
    }

    // 선택한 일정 삭제
    public void deleteSchedule(Long id, String password) {
        Schedule schedule = scheduleRepository.findById(id);

        if (!schedule.getPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        scheduleRepository.deleteSchedule(id);
    }
}
