package com.medilabo.patient.controller;

import com.medilabo.patient.dto.RiskReportDto;
import com.medilabo.patient.model.PatientModel;
import com.medilabo.patient.service.PatientService;
import com.medilabo.patient.service.RiskClient;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientViewController.class)
class PatientViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private RiskClient riskClient;

    // =========================
    // LISTE
    // =========================
    @Test
    void showPatientsListTest() throws Exception {
        when(patientService.findAll()).thenReturn(List.of(new PatientModel(), new PatientModel()));

        mockMvc.perform(get("/patients/ui/liste"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients-liste"))
                .andExpect(model().attributeExists("patients"));

        verify(patientService).findAll();

    }

    // =========================
    // AJOUTER (GET)
    // =========================
    @Test
    void showFormulaireAddPatient() throws Exception {
        mockMvc.perform(get("/patients/ui/ajouter"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient-ajouter"))
                .andExpect(model().attributeExists("patient"));

        verifyNoInteractions(patientService, riskClient);
    }

    // =========================
    // AJOUTER (POST)
    // =========================
    @Test
    void registrationAjoutPatientTest() throws Exception {
        when(patientService.save(any(PatientModel.class))).thenReturn(new PatientModel());

        mockMvc.perform(post("/patients/ui/ajouter")
                        .param("firstName", "Olivier")
                        .param("lastName", "Serra"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/ui/liste"));

        verify(patientService).save(any(PatientModel.class));
    }

    // =========================
    // MODIFIER (GET)
    // =========================
    @Test
    void showFormulaireModifyTest() throws Exception {
        PatientModel p = new PatientModel();
        p.setId(1L);
        when(patientService.findById(1L)).thenReturn(p);

        mockMvc.perform(get("/patients/ui/modifier/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient-modifier"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("patient", p));


        verify(patientService).findById(1L);

    }

    @Test
    void showFormulaireModifyWithoutPatientTest() throws Exception {
        when(patientService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/patients/ui/modifier/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/ui/liste"));

        verify(patientService).findById(1L);

    }



    // =========================
    // SUPPRIMER (GET)
    // =========================
    @Test
    void confirmationSupprPatient() throws Exception {
        PatientModel p = new PatientModel();
        p.setId(7L);
        when(patientService.findById(7L)).thenReturn(p);

        mockMvc.perform(get("/patients/ui/supprimer/7"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient-supprimer"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("patient", p));

        verify(patientService).findById(7L);

    }

    // =========================
    // DETAIL
    // =========================
    @Test
    void showDetailPatientTest() throws Exception {
        PatientModel p = new PatientModel();
        p.setId(3L);

        RiskReportDto risk = new RiskReportDto(); // adapte si ton DTO a des champs obligatoires

        when(patientService.findById(3L)).thenReturn(p);
        when(riskClient.getRiskForPatient(3L)).thenReturn(risk);

        mockMvc.perform(get("/patients/ui/detail/3"))
                .andExpect(status().isOk())
                .andExpect(view().name("patient-detail"))
                .andExpect(model().attribute("patient", p))
                .andExpect(model().attribute("risk", risk));

        verify(patientService).findById(3L);

    }

    @Test
    void showDetailPatientWithoutPatient() throws Exception {
        when(patientService.findById(3L)).thenReturn(null);

        mockMvc.perform(get("/patients/ui/detail/3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/ui/liste"));

        verify(patientService).findById(3L);

    }
}
