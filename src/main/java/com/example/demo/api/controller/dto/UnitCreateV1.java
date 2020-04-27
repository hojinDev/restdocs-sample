package com.example.demo.api.controller.dto;

import com.example.demo.domain.Gender;
import com.example.demo.domain.Job;
import com.example.demo.service.dto.UnitCreateDto;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class UnitCreateV1 {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotNull(message = "직업은 필수입니다.")
    private JobV1 job;

    @NotNull(message = "성별은 필수입니다.")
    private Gender gender;

    @NotNull(message = "생일은 필수입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String hobby;

    public UnitCreateDto toDto() {
        return UnitCreateDto.builder()
                .name(name)
                .job(Job.valueOf(job.name()))
                .gender(gender)
                .birthDate(birthDate)
                .hobby(hobby)
                .build();
    }
}
