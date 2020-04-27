package com.example.demo.api.controller;

import com.example.demo.api.controller.dto.UnitCreateV2;
import com.example.demo.api.controller.dto.UnitUpdateV2;
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
public class UnitControllerV2 {

    private final UnitService unitService;

    @PostMapping("/v2/units")
    public ApiResponseDto<UnitDto> add(@Valid @RequestBody UnitCreateV2 create) {

        return ApiResponseDto.createOK(unitService.add(create.toDto()));
    }

    @PutMapping("/v2/units/{id}")
    public ApiResponseDto<UnitDto> update(@PathVariable("id") Long id, @Valid @RequestBody UnitUpdateV2 update) {

        return ApiResponseDto.createOK(unitService.update(id, update.toDto()));
    }
}
