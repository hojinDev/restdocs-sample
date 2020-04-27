package com.example.demo.api.controller.dto;

import com.example.demo.utils.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum JobV3 implements EnumType {
    STUDENT("학생"),
    EMPLOYEE("회사원"),
    HOMEMAKER("주부"),
    EMPTY("없음");

    private final String text;

    @Override
    public String getId() {
        return this.name();
    }
}
