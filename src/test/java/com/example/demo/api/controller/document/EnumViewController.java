package com.example.demo.api.controller.document;

import com.example.demo.domain.Gender;
import com.example.demo.response.ApiResponseCode;
import com.example.demo.response.ApiResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EnumViewController {

    @GetMapping("/docs")
    public ApiResponseDto<Docs> findAll() {

        Map<String, String> apiResponseCodes = Arrays.stream(ApiResponseCode.values())
                .collect(Collectors.toMap(ApiResponseCode::name, ApiResponseCode::getText));

        Map<String, String> gender = Arrays.stream(Gender.values())
                .collect(Collectors.toMap(Gender::name, Gender::getText));

        return ApiResponseDto.createOK(
                Docs.builder()
                        .apiResponseCodes(apiResponseCodes)
                        .genders(gender)
                        .build()
        );
    }
}
