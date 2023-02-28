package com.example.leaderIt_project.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "/hibernate-test.properties")
class IotDeviceControllerTest {

    @Autowired
    private IotDeviceController iotDeviceController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll_TestTypeParameter() throws Exception {
        mockMvc.perform(get("/devices").param("type", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serialNumber").value("1234567890"))
                .andExpect(jsonPath("$[0].deviceName").value("device_1"));
    }

    @Test
    void getAll_TestDateOfCreateParameter() throws Exception {
        mockMvc.perform(get("/devices").param("date_of_create", "2023-02-21"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serialNumber").value("1234567899"))
                .andExpect(jsonPath("$[0].deviceName").value("device_2"));
    }

    @Test
    void getAll_TestPagination() throws Exception {
        mockMvc.perform(get("/devices").param("page", "1").param("count", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serialNumber").value("1234567890"))
                .andExpect(jsonPath("$[0].deviceName").value("device_1"))
                .andExpect(jsonPath("$[1].serialNumber").value("1234567899"))
                .andExpect(jsonPath("$[1].deviceName").value("device_2"));
    }

    @Test
    void getAll_TestAllParameters() throws Exception {
        mockMvc.perform(get("/devices").param("page", "1").param("count", "2")
                        .param("date_of_crete", "2023-02-25").param("type", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].serialNumber").value("1234567895"))
                .andExpect(jsonPath("$[0].deviceName").value("device_3"));
    }

    @Test
    void getOneDeviceBySerialNumber_SuccessFound() throws Exception {
        mockMvc.perform(get("/devices/1234567895"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serialNumber").value("1234567895"))
                .andExpect(jsonPath("$.deviceName").value("device_3"));
    }

    @Test
    void getOneDeviceBySerialNumber_NotFound() throws Exception {
        mockMvc.perform(get("/devices/123"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getAllOccasionsBySerialNumber_WithoutParameters() throws Exception {
        mockMvc.perform(get("/devices/1234567890/occasions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].occasionType").value("Count calls on the birthday"))
                .andExpect(jsonPath("$[1].occasionType").value("Count calls on the Sunday"));
    }

    @Test
    void getAllOccasionsBySerialNumber_WithDateOfCreateParameter() throws Exception {
        mockMvc.perform(get("/devices/1234567890/occasions").param("date_of_create", "2023-02-16"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].occasionType").value("Count calls on the birthday"));
    }

    @Test
    void getAllOccasionsBySerialNumber_PaginationTest() throws Exception {
        mockMvc.perform(get("/devices/1234567890/occasions").param("page", "1").param("count", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].occasionType").value("Count calls on the birthday"));
    }

    @Test
    void saveDevice() throws Exception {
        mockMvc.perform(post("/devices").param("serialNumber", "7888")
                .param("deviceName", "device_123").param("deviceType", "0"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}