package com.example.demo.api.controller.dto;

import com.example.demo.domain.Job;
import com.example.demo.service.dto.UnitUpdateDto;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UnitUpdateV2 {

    @NotNull(message = "직업은 필수입니다.")
    private JobV2 job;

    private String hobby;

    public UnitUpdateDto toDto() {
        return UnitUpdateDto.builder()
                .job(Job.valueOf(job.name()))
                .hobby(hobby)
                .build();
    }
}
