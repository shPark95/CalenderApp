package com.server.calendarapp.domain.schedule.controller;

import com.server.calendarapp.domain.schedule.dto.ScheduleDto;
import com.server.calendarapp.domain.schedule.model.Schedule;
import com.server.calendarapp.domain.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleDto> create(@RequestBody Schedule schedule) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ScheduleDto(scheduleService.createSchedule(schedule)));
    }

    @GetMapping
    public List<ScheduleDto> getSchedules(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long memberId
    ) {
        return scheduleService.getSchedules(startDate, endDate, memberId);
    }

    // 페이지네이션
    @GetMapping("/paged")
    public List<ScheduleDto> getPagedSchedules(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        System.out.println("Fetching paged schedules with page: " + page + " and size: " + size);
        return scheduleService.getPagedSchedules(page, size);
    }

    // 선택한 일정 ID로 조회 (단건 조회)
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable Long id) {
        Schedule s = scheduleService.getScheduleById(id);
        return s == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(new ScheduleDto(s));
    }

    // 선택한 일정 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleDto> updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
        Schedule updated = scheduleService.updateSchedule(id, schedule);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(new ScheduleDto(updated));
    }

    // 선택한 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String password = body.get("password");
        boolean result = scheduleService.deleteSchedule(id, password);
        if (!result) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(Map.of("message", "삭제 완료!"));
    }
}
