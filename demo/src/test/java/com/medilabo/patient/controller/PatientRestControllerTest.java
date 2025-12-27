package com.medilabo.patient.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.patient.dto.NoteDto;
import com.medilabo.patient.service.NoteClient;
import com.medilabo.patient.model.PatientModel;
import com.medilabo.patient.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientRestController.class)
class PatientRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PatientService patientService;

    @MockBean
    NoteClient noteClient;

    @Test
    void getAllPatientsTest() throws Exception {
        when(patientService.findAll()).thenReturn(List.of(new PatientModel(), new PatientModel()));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk());

        verify(patientService).findAll();
    }

    @Test
    void getNoPatientByIdTest() throws Exception {
        when(patientService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/patients/99"))
                .andExpect(status().isNotFound());

        verify(patientService).findById(99L);
    }

    @Test
    void getPatientByIdTest() throws Exception {
        PatientModel p = new PatientModel();
        p.setId(1L);
        when(patientService.findById(1L)).thenReturn(p);

        mockMvc.perform(get("/patients/1"))
                .andExpect(status().isOk());

        verify(patientService).findById(1L);

    }

    @Test
    void getNoPatientfindByNamesTest() throws Exception {
        when(patientService.findByNames("Serra", "dédé")).thenReturn(null);

        mockMvc.perform(get("/patients/search")
                        .param("lastName", "Serra")
                        .param("firstName", "dédé"))
                .andExpect(status().isNotFound());

        verify(patientService).findByNames("Serra", "dédé");
        }

    @Test
    void getPatientfindByNamesTest() throws Exception {
        when(patientService.findByNames("Serra", "dédé")).thenReturn(new PatientModel());

        mockMvc.perform(get("/patients/search")
                        .param("lastName", "Serra")
                        .param("firstName", "dédé"))
                .andExpect(status().isOk());

        verify(patientService).findByNames("Serra", "dédé");

    }

    @Test
    void createPatientTest() throws Exception {
        PatientModel saved = new PatientModel();
        saved.setId(10L);

        when(patientService.save(any(PatientModel.class))).thenReturn(saved);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PatientModel())))
                .andExpect(status().isCreated());

        verify(patientService).save(any(PatientModel.class));

    }

    @Test
    void updateNoPatientTest() throws Exception {
        when(patientService.findById(5L)).thenReturn(null);

        mockMvc.perform(put("/patients/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PatientModel())))
                .andExpect(status().isNotFound());

        verify(patientService).findById(5L);
        verify(patientService, never()).save(any());

    }

    @Test
    void updatePatientTest() throws Exception {
        when(patientService.findById(5L)).thenReturn(new PatientModel());
        when(patientService.save(any(PatientModel.class))).thenReturn(new PatientModel());

        mockMvc.perform(put("/patients/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new PatientModel())))
                .andExpect(status().isOk());

        verify(patientService).findById(5L);
        verify(patientService).save(any(PatientModel.class));

    }

    @Test
    void deleteNoPatientTest() throws Exception {
        when(patientService.findById(6L)).thenReturn(null);

        mockMvc.perform(delete("/patients/6"))
                .andExpect(status().isNotFound());

        verify(patientService).findById(6L);
        verify(patientService, never()).deleteById(anyLong());

    }

    @Test
    void deletePatientTest() throws Exception {
        when(patientService.findById(6L)).thenReturn(new PatientModel());

        mockMvc.perform(delete("/patients/6"))
                .andExpect(status().isNoContent());

        verify(patientService).findById(6L);
        verify(patientService).deleteById(6L);
        verifyNoInteractions(noteClient);
    }

    @Test
    void getNotesWithNoPatientTest() throws Exception {
        when(patientService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/patients/1/notes"))
                .andExpect(status().isNotFound());

        verify(patientService).findById(1L);

    }

    @Test
    void getNotesWithPatientTest() throws Exception {
        when(patientService.findById(1L)).thenReturn(new PatientModel());
        when(noteClient.getNotesForPatient(1L)).thenReturn(List.of(mock(NoteDto.class)));

        mockMvc.perform(get("/patients/1/notes"))
                .andExpect(status().isOk());

        verify(patientService).findById(1L);
        verify(noteClient).getNotesForPatient(1L);
    }

}
