package com.example.demo.api.controller.document;

import com.example.demo.api.controller.UnitControllerV2;
import com.example.demo.service.UnitService;
import com.example.demo.service.dto.UnitDto;
import com.example.demo.service.dto.UnitUpdateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.demo.api.controller.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.example.demo.api.controller.document.utils.ApiDocumentUtils.getDocumentResponse;
import static com.example.demo.api.controller.document.utils.DocumentFormatGenerator.getDateFormat;
import static com.example.demo.domain.Gender.MALE;
import static com.example.demo.domain.Job.HOMEMAKER;
import static java.time.LocalDate.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UnitControllerV2.class)
@AutoConfigureRestDocs
public class UnitUpdateV2DocumentationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UnitService UnitService;

    @Test
    public void update() throws Exception {

        //given
        given(UnitService.update(eq(1L), any(UnitUpdateDto.class)))
                .willReturn(UnitDto.builder()
                        .id(1L)
                        .name("고길동")
                        .job(HOMEMAKER)
                        .gender(MALE)
                        .birthDate(of(1958, 9, 19))
                        .hobby("TV 시청")
                        .build());

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.job = "HOMEMAKER";
        updateRequest.hobby = "TV 시청";

        //when
        ResultActions result = this.mockMvc.perform(
                put("/v2/units/{id}", 1L)
                        .content(objectMapper.writeValueAsString(updateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("units-update-v2",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("아이디")
                        ),
                        requestFields(
                                fieldWithPath("job").type(JsonFieldType.STRING).description("직업"),
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
    public static class UpdateRequest {
        String job;
        String hobby;
    }
}
