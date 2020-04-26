package com.example.demo.domain;

import com.example.demo.utils.EnumType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender implements EnumType {
    MALE("남자"),
    FEMALE("여자");

    private final String text;

    @Override
    public String getId() {
        return this.name();
    }
}
