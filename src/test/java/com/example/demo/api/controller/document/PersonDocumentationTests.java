package com.example.demo.api.controller.document;

import com.example.demo.api.controller.PersonController;
import com.example.demo.domain.Gender;
import com.example.demo.service.PersonService;
import com.example.demo.service.dto.PersonDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.api.controller.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.example.demo.api.controller.document.utils.ApiDocumentUtils.getDocumentResponse;
import static com.example.demo.api.controller.document.utils.DocumentFormatGenerator.getDateFormat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
@AutoConfigureRestDocs
public class PersonDocumentationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonService personService;

    @Test
    public void findAll() throws Exception {

        //given
        List<PersonDto.Response> responseList = Arrays.asList(
                PersonDto.Response.builder()
                        .id(1L)
                        .firstName("호진")
                        .lastName("이")
                        .gender(Gender.MALE)
                        .birthDate(LocalDate.of(1983, 7, 4))
                        .hobby("놀기")
                        .build(),
                PersonDto.Response.builder()
                        .id(2L)
                        .firstName("호석")
                        .lastName("이")
                        .gender(Gender.MALE)
                        .birthDate(LocalDate.of(1985, 2, 1))
                        .hobby("신나게놀기")
                        .build()
        );

        given(personService.findAll())
                .willReturn(responseList);

        //when
        ResultActions result = this.mockMvc.perform(
                get("/persons")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("persons-find-all",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("persons[].id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("persons[].firstName").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("persons[].lastName").type(JsonFieldType.STRING).description("성"),
                                fieldWithPath("persons[].age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("persons[].birthDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("생년월일"),
                                fieldWithPath("persons[].hobby").type(JsonFieldType.STRING).description("취미"),
                                fieldWithPath("persons[].gender").type(JsonFieldType.STRING).description("성별")
                        )
                ));
    }

    @Test
    public void findById() throws Exception {

        //given
        given(personService.findById(1L))
                .willReturn(PersonDto.Response.builder()
                        .id(1L)
                        .firstName("호진")
                        .lastName("이")
                        .gender(Gender.MALE)
                        .birthDate(LocalDate.of(1983, 7, 4))
                        .hobby("놀기")
                        .build()
                );

        //when
        ResultActions result = this.mockMvc.perform(
                get("/persons/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("persons-find",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("아이디")
                        ),
                        responseFields(beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("person.id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("person.firstName").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("person.lastName").type(JsonFieldType.STRING).description("성"),
                                fieldWithPath("person.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("person.birthDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("생년월일"),
                                fieldWithPath("person.gender").type(JsonFieldType.STRING).description("성별"),
                                fieldWithPath("person.hobby").type(JsonFieldType.STRING).description("취미")
                        )
                ));
    }

    @Test
    public void add() throws Exception {

        //given
        PersonDto.Response response = PersonDto.Response.builder()
                .id(1L)
                .firstName("호진")
                .lastName("이")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1983, 7, 4))
                .hobby("놀기")
                .build();

        given(personService.add(any(PersonDto.Create.class)))
                .willReturn(response);

        //when
        PersonDto.Create create = new PersonDto.Create();
        create.setFirstName("호진");
        create.setLastName("이");
        create.setBirthDate(LocalDate.of(1983, 7, 4));
        create.setGender(Gender.MALE);
        create.setHobby("놀기");

        ResultActions result = this.mockMvc.perform(
                post("/persons")
                        .content(objectMapper.writeValueAsString(create))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("persons-add",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("firstName").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("lastName").type(JsonFieldType.STRING).description("성"),
                                fieldWithPath("birthDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("생년월일"),
                                fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미").optional()
                        ),
                        responseFields(beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("person.id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("person.firstName").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("person.lastName").type(JsonFieldType.STRING).description("성"),
                                fieldWithPath("person.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("person.birthDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("생년월일"),
                                fieldWithPath("person.gender").type(JsonFieldType.STRING).description("성별"),
                                fieldWithPath("person.hobby").type(JsonFieldType.STRING).description("취미")
                        )
                ));
    }

    @Test
    public void delete_by_id() throws Exception {

        //given
        doNothing()
                .when(personService)
                .delete(1L);

        //when
        ResultActions result = this.mockMvc.perform(
                delete("/persons/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("persons-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("아이디")
                        )
                ));
    }

    @Test
    public void update() throws Exception {

        //given
        PersonDto.Response response = PersonDto.Response.builder()
                .id(1L)
                .firstName("호석")
                .lastName("이")
                .birthDate(LocalDate.of(1985, 2, 1))
                .gender(Gender.MALE)
                .hobby("신나게 놀기")
                .build();

        given(personService.update(eq(1L), any(PersonDto.Update.class)))
                .willReturn(response);

        //when
        PersonDto.Update update = new PersonDto.Update();
        update.setFirstName("호석");
        update.setLastName("이");
        update.setBirthDate(LocalDate.of(1985, 2, 1));
        update.setHobby("신나게 놀기");


        ResultActions result = this.mockMvc.perform(
                put("/persons/{id}", 1L)
                        .content(objectMapper.writeValueAsString(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("persons-update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("아이디")
                        ),
                        requestFields(
                                fieldWithPath("firstName").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("lastName").type(JsonFieldType.STRING).description("성"),
                                fieldWithPath("birthDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("생년월일"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미").optional()
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("person.id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("person.firstName").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("person.lastName").type(JsonFieldType.STRING).description("성"),
                                fieldWithPath("person.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("person.birthDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("생년월일"),
                                fieldWithPath("person.gender").type(JsonFieldType.STRING).description("성별"),
                                fieldWithPath("person.hobby").type(JsonFieldType.STRING).description("취미")
                        )
                ));
    }
}