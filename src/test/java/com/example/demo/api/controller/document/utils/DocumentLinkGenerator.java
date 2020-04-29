package com.example.demo.api.controller.document.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface DocumentLinkGenerator {

    static String generateLinkCode(DocUrl docUrl) {
        return String.format("link:common/%s.html[%s %s,role=\"popup\"]", docUrl.pageId, docUrl.text, "코드");
    }

    static String generateText(DocUrl docUrl) {
        return String.format("%s %s", docUrl.text, "코드명");
    }

    @RequiredArgsConstructor
    enum DocUrl {
        JOB("job", "직업"),
        JOBV1("jobV1", "직업"),
        JOBV2("jobV2", "직업"),
        JOBV3("jobV3", "직업"),
        GENDER("gender", "성별"),
        ;

        private final String pageId;
        @Getter
        private final String text;
    }
}
