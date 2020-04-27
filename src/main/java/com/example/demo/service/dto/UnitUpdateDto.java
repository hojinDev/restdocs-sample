package com.example.demo.service.dto;

import com.example.demo.domain.Job;
import com.example.demo.domain.Unit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UnitUpdateDto {

    private Job job;
    private String hobby;

    @Builder
    private UnitUpdateDto(Job job, String hobby) {
        this.job = job;
        this.hobby = hobby;
    }

    public void apply(Unit unit) {
        unit.update(job, hobby);
    }
}
