package com.example.demo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Unit {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Job job;

    @Column(nullable = false)
    private LocalDate birthDate;

    private String hobby;

    @Builder
    private Unit(String name, Gender gender, Job job, LocalDate birthDate, String hobby) {
        this.name = name;
        this.gender = gender;
        this.job = job;
        this.birthDate = birthDate;
        this.hobby = hobby;
    }

    public void update(Job job, String hobby) {
        this.job = job;
        this.hobby = hobby;
    }
}
