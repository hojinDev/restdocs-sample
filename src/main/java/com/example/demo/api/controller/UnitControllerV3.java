package com.example.demo.api.controller;

import com.example.demo.api.controller.dto.UnitCreateV3;
import com.example.demo.api.controller.dto.UnitUpdateV3;
import com.example.demo.response.ApiResponseDto;
import com.example.demo.service.UnitService;
import com.example.demo.service.dto.UnitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UnitControllerV3 {

    private final UnitService unitService;

    @PostMapping("/v3/units")
    public ApiResponseDto<UnitDto> add(@Valid @RequestBody UnitCreateV3 create) {

        return ApiResponseDto.createOK(unitService.add(create.toDto()));
    }

    @PutMapping("/v3/units/{id}")
    public ApiResponseDto<UnitDto> update(@PathVariable("id") Long id, @Valid @RequestBody UnitUpdateV3 update) {

        return ApiResponseDto.createOK(unitService.update(id, update.toDto()));
    }
}
