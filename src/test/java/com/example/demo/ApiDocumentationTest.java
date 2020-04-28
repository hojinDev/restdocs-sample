package com.example.demo;

import com.example.demo.api.controller.UnitController;
import com.example.demo.api.controller.UnitControllerV1;
import com.example.demo.api.controller.UnitControllerV2;
import com.example.demo.api.controller.UnitControllerV3;
import com.example.demo.api.controller.api.controller.EnumViewController;
import com.example.demo.service.UnitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {
        UnitControllerV1.class,
        UnitControllerV2.class,
        UnitControllerV3.class,
        UnitController.class,
        EnumViewController.class
})
@AutoConfigureRestDocs
public abstract class ApiDocumentationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UnitService unitService;
}
