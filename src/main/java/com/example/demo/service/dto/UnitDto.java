package com.example.demo.service.dto;

import com.example.demo.domain.Gender;
import com.example.demo.domain.Job;
import com.example.demo.domain.Unit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UnitDto {

    private Long id;
    private String name;
    private Job job;
    private String jobName;
    private Gender gender;
    private String genderName;
    private LocalDate birthDate;
    private String hobby;

    @Builder
    private UnitDto(Long id, String name, Job job, Gender gender, LocalDate birthDate, String hobby) {
        this.id = id;
        this.name = name;
        this.job = job;
        this.jobName = job.getText();
        this.gender = gender;
        this.genderName = gender.getText();
        this.birthDate = birthDate;
        this.hobby = hobby;
    }

    public static UnitDto of(Unit unit) {
        return UnitDto.builder()
                .id(unit.getId())
                .name(unit.getName())
                .job(unit.getJob())
                .gender(unit.getGender())
                .birthDate(unit.getBirthDate())
                .hobby(unit.getHobby())
                .build();
    }
}
