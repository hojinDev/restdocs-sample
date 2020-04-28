package com.example.demo.api.controller.document;

import com.example.demo.ApiDocumentationTest;
import com.example.demo.service.dto.UnitCreateDto;
import com.example.demo.service.dto.UnitDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.demo.api.controller.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.example.demo.api.controller.document.utils.ApiDocumentUtils.getDocumentResponse;
import static com.example.demo.api.controller.document.utils.DocumentFormatGenerator.getDateFormat;
import static com.example.demo.domain.Gender.MALE;
import static com.example.demo.domain.Job.EMPLOYEE;
import static java.time.LocalDate.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UnitCreateV1DocumentationTests extends ApiDocumentationTest {

    @Test
    public void add() throws Exception {

        //given
        given(unitService.add(any(UnitCreateDto.class)))
                .willReturn(UnitDto.builder()
                        .id(1L)
                        .name("고길동")
                        .job(EMPLOYEE)
                        .gender(MALE)
                        .birthDate(of(1958, 9, 19))
                        .hobby("바둑")
                        .build());

        Request request = new Request();
        request.name = "고길동";
        request.job = "EMPLOYEE";
        request.gender = "MALE";
        request.birthDate = "1958-09-19";
        request.hobby = "바둑";

        //when
        ResultActions result = this.mockMvc.perform(
                post("/v1/units")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("units-add-v1",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("job").type(JsonFieldType.STRING).description("직업 코드"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("성별 코드"),
                                fieldWithPath("birthDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("생년월일"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미").optional()
                        ),
                        responseFields(beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("job").type(JsonFieldType.STRING).description("직업 코드"),
                                fieldWithPath("jobName").type(JsonFieldType.STRING).description("직업 명"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("성별 코드"),
                                fieldWithPath("genderName").type(JsonFieldType.STRING).description("성별 명"),
                                fieldWithPath("birthDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("생년월일"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                        )
                ));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Request {
        String name;
        String job;
        String gender;
        String birthDate;
        String hobby;
    }
}
