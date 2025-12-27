package com.medilabo.RiskService.controller;

import com.medilabo.RiskService.riskController.RiskRestController;
import com.medilabo.RiskService.service.RiskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RiskRestController.class)
class RiskRestControllerUpTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RiskService riskService;

    @Test
    void RiskRestServiceControllerTest() throws Exception {
        mockMvc.perform(get("/risk"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Risk service is UP")));
    }
}
