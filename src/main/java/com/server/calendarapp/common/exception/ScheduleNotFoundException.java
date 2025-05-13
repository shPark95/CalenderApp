package com.server.calendarapp.common.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException() {
        super("일정을 찾을 수 없습니다.");
    }

    public ScheduleNotFoundException(Long id) {
        super("일정번호(" + id + ")를 찾을 수 없습니다.");
    }
}
