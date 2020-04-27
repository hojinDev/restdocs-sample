package com.example.demo.service.dto;

import com.example.demo.domain.Gender;
import com.example.demo.domain.Job;
import com.example.demo.domain.Unit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UnitCreateDto {

    private String name;
    private Job job;
    private Gender gender;
    private LocalDate birthDate;
    private String hobby;

    @Builder
    private UnitCreateDto(String name, Job job, Gender gender, LocalDate birthDate, String hobby) {
        this.name = name;
        this.job = job;
        this.gender = gender;
        this.birthDate = birthDate;
        this.hobby = hobby;
    }

    public Unit toEntity() {
        return Unit.builder()
                .name(name)
                .job(job)
                .gender(gender)
                .birthDate(birthDate)
                .hobby(hobby)
                .build();
    }
}
