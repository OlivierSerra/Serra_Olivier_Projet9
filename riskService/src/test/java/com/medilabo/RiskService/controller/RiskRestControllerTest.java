package com.medilabo.RiskService.controller;

import com.medilabo.RiskService.dto.RiskReportDto;
import com.medilabo.RiskService.riskController.RiskRestController;
import com.medilabo.RiskService.service.RiskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RiskRestController.class)
class RiskRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RiskService riskService;

    @Test
    void getRiskTest() throws Exception {
        when(riskService.assessRisk(1L)).thenReturn(new RiskReportDto()); // DTO vide OK pour status 200

        mockMvc.perform(get("/risk/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(riskService).assessRisk(1L);
    }
}
