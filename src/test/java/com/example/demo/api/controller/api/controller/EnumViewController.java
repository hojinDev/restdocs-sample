package com.example.demo.api.controller.api.controller;

import com.example.demo.api.controller.dto.JobV1;
import com.example.demo.api.controller.dto.JobV2;
import com.example.demo.api.controller.dto.JobV3;
import com.example.demo.domain.Gender;
import com.example.demo.domain.Job;
import com.example.demo.response.ApiResponseCode;
import com.example.demo.response.ApiResponseDto;
import com.example.demo.utils.EnumType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EnumViewController {

    @GetMapping("/docs")
    public ApiResponseDto<Docs> findAll() {

        Map<String, String> apiResponseCodes = getDocs(ApiResponseCode.values());
        Map<String, String> gender = getDocs(Gender.values());
        Map<String, String> jobs = getDocs(Job.values());
        Map<String, String> jobsV1 = getDocs(JobV1.values());
        Map<String, String> jobsV2 = getDocs(JobV2.values());
        Map<String, String> jobsV3 = getDocs(JobV3.values());

        return ApiResponseDto.createOK(
                Docs.testBuilder()
                        .apiResponseCodes(apiResponseCodes)
                        .genders(gender)
                        .jobs(jobs)
                        .jobsV1(jobsV1)
                        .jobsV2(jobsV2)
                        .jobsV3(jobsV3)
                        .build()
        );
    }

    private Map<String, String> getDocs(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes)
                .collect(Collectors.toMap(EnumType::getId, EnumType::getText));
    }
}
