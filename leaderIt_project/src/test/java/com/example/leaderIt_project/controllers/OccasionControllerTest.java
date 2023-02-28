package com.example.leaderIt_project.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "/hibernate-test.properties")
class OccasionControllerTest {

    @Autowired
    private OccasionController occasionController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllOccasions() throws Exception {
        mockMvc.perform(get("/occasions"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andExpect(jsonPath("$[0].deviceID").value(1))
                .andExpect(jsonPath("$[1].deviceID").value(1))
                .andExpect(jsonPath("$[2].deviceID").value(2))
                .andExpect(jsonPath("$[3].deviceID").value(2))
                .andExpect(jsonPath("$[4].deviceID").value(3));
    }

    @Test
    void getOne_Success() throws Exception {
        mockMvc.perform(get("/occasions/3"))
                .andDo(print())
                .andExpect(jsonPath("$.deviceID").value(2));
    }

    @Test
    void saveOccasion() throws Exception {
        mockMvc.perform(post("/occasions").param("deviceID", "10"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getStatistics_WithoutParameters() throws Exception {
        mockMvc.perform(get("/occasions/statistic"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.SMART_MOBILE").value(2))
                .andExpect(jsonPath("$.SMART_FRIDGE").value(2))
                .andExpect(jsonPath("$.SMART_WATCH").value(1));
    }


    @Test
    void getStatistics_WithParameters() throws Exception {
        mockMvc.perform(get("/occasions/statistic").param("start_date", "2023-02-16")
                        .param("end_date", "2023-02-20"))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.SMART_MOBILE").value(2));
    }
}