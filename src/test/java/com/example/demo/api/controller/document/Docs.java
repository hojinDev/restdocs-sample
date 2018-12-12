package com.example.demo.api.controller.document;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
class Docs {

    Map<String, String> genders;
    Map<String, String> apiResponseCodes;

    @Builder
    private Docs(Map<String, String> genders, Map<String, String> apiResponseCodes) {
        this.genders = genders;
        this.apiResponseCodes = apiResponseCodes;
    }
}
