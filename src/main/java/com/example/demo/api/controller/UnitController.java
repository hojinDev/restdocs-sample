package com.example.demo.api.controller;

import com.example.demo.response.ApiResponseDto;
import com.example.demo.service.UnitService;
import com.example.demo.service.dto.UnitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @GetMapping("/v1/units")
    public ApiResponseDto<List<UnitDto>> findAll() {

        return ApiResponseDto.createOK(unitService.findAll());
    }

    @GetMapping("/v1/units/{id}")
    public ApiResponseDto<UnitDto> findById(@PathVariable("id") Long id) {

        return ApiResponseDto.createOK(unitService.findById(id));
    }

    @DeleteMapping("/v1/units/{id}")
    public ApiResponseDto<String> delete(@PathVariable("id") Long id) {

        unitService.delete(id);

        return ApiResponseDto.DEFAULT_OK;
    }
}
