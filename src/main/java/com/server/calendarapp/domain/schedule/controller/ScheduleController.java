package com.server.calendarapp.domain.schedule.controller;

import com.server.calendarapp.domain.schedule.dto.ScheduleDto;
import com.server.calendarapp.domain.schedule.model.Schedule;
import com.server.calendarapp.domain.schedule.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public String create(@RequestBody ScheduleDto scheduleDto) {
        scheduleService.createSchedule(scheduleDto);
        return "등록 완료!";
    }

    @GetMapping
    public List<Schedule> getSchedules(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String author
    ) {
        return scheduleService.getSchedules(startDate, endDate, author);
    }

    // 선택한 일정 ID로 조회 (단건 조회)
    @GetMapping("/{id}")
    public Schedule getScheduleById(@PathVariable Long id) {
        return scheduleService.getScheduleById(id);
    }

    // 선택한 일정 수정
    @PatchMapping("/{id}")
    public String updateSchedule(@PathVariable Long id, @RequestBody ScheduleDto dto) {
        scheduleService.updateSchedule(id, dto);
        return "수정 완료!";
    }

    // 선택한 일정 삭제
    @DeleteMapping("/{id}")
    public String deleteSchedule(@PathVariable Long id, @RequestBody ScheduleDto dto) {
        scheduleService.deleteSchedule(id, dto.getPassword());
        return "삭제 완료!";
    }
}
