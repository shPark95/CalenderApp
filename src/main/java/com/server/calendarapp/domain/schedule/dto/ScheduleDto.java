package com.server.calendarapp.domain.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDto {
    private Long id;
    @NotBlank(message = "할일은 필수입니다.")
    @Size(max = 200, message = "할일은 200자 이내로 입력해주세요.")
    private String title;
    @NotBlank(message = "작성자명은 필수입니다.")
    private String author;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
