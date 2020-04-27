package com.example.demo.api.controller.api.controller;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class Docs {

    Map<String, String> apiResponseCodes;
    Map<String, String> genders;
    Map<String, String> jobs;
    Map<String, String> jobsV1;
    Map<String, String> jobsV2;
    Map<String, String> jobsV3;

    @Builder(builderClassName = "TestBuilder", builderMethodName = "testBuilder")
    private Docs(Map<String, String> apiResponseCodes, Map<String, String> genders, Map<String, String> jobs, Map<String, String> jobsV1, Map<String, String> jobsV2, Map<String, String> jobsV3) {
        this.apiResponseCodes = apiResponseCodes;
        this.genders = genders;
        this.jobs = jobs;
        this.jobsV1 = jobsV1;
        this.jobsV2 = jobsV2;
        this.jobsV3 = jobsV3;
    }
}
