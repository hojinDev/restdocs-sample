package com.example.demo.api.controller.document;

import com.example.demo.api.controller.UnitController;
import com.example.demo.service.UnitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.example.demo.api.controller.document.utils.ApiDocumentUtils.getDocumentRequest;
import static com.example.demo.api.controller.document.utils.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UnitController.class)
@AutoConfigureRestDocs
public class UnitDeleteDocumentationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UnitService UnitService;

    @Test
    public void delete_by_id() throws Exception {

        //given
        doNothing()
                .when(UnitService)
                .delete(1L);

        //when
        ResultActions result = this.mockMvc.perform(
                delete("/v1/units/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("units-delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("아이디")
                        )
                ));
    }
}
