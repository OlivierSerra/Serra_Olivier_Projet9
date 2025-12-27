package com.openclassroom.frontService.controller;

import com.openclassroom.frontService.dto.PatientDto;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FrontRoutesControllerTest {

    @Test
    void showPatientList() {
        RestTemplate restTemplate = mock(RestTemplate.class);
        FrontRoutesController controller = new FrontRoutesController(restTemplate);

        when(restTemplate.getForObject(
                eq("http://patientservice:8081/patients"),
                eq(PatientDto[].class)
        )).thenReturn(new PatientDto[]{new PatientDto(), new PatientDto()});

        Model model = new ExtendedModelMap();
        String view = controller.listePatients(model);

        assertEquals("patients-liste", view);

        Object attr = model.getAttribute("patients");
        assertNotNull(attr);
        assertTrue(attr instanceof List);
        assertEquals(2, ((List<?>) attr).size());
    }
}
