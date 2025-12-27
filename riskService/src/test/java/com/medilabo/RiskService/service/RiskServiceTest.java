package com.medilabo.RiskService.service;

import com.medilabo.RiskService.client.NoteClient;
import com.medilabo.RiskService.client.PatientClient;
import com.medilabo.RiskService.dto.NoteDto;
import com.medilabo.RiskService.dto.PatientDto;
import com.medilabo.RiskService.dto.RiskReportDto;
import com.medilabo.RiskService.model.RiskLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class RiskServiceTest {

    private PatientClient patientClient;
    private NoteClient noteClient;
    private RiskService riskService;

    @BeforeEach
    void setUp() {
        patientClient = mock(PatientClient.class);
        noteClient = mock(NoteClient.class);
        riskService = new RiskService(patientClient, noteClient);
    }

    private static PatientDto patient(Long id, String first, String last, String gender, LocalDate birthDate) {
        PatientDto p = new PatientDto();
        p.setId(String.valueOf(id));
        p.setFirstName(first);
        p.setLastName(last);
        p.setGender(gender);
        p.setBirthDate(birthDate);
        return p;
    }

    private static NoteDto note(String text) {
        NoteDto n = new NoteDto();
        n.setNote(text);
        return n;
    }

    @Test
    void riskWithNoPatient() {
        Long patientId = 1L;
        when(patientClient.getPatientById(patientId)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> riskService.assessRisk(patientId));
        assertTrue(ex.getMessage().contains("Patient not found"));
        verify(noteClient, never()).getNotesForPatient(anyLong());
    }

    @Test
    void riskWithNoNoteId() {
        Long patientId = 1L;
        PatientDto p = patient(patientId, "dédé", "Serra", "M", LocalDate.now().minusYears(40));
        when(patientClient.getPatientById(patientId)).thenReturn(p);
        when(noteClient.getNotesForPatient(patientId)).thenReturn(null);

        RiskReportDto report = riskService.assessRisk(patientId);

        assertEquals(0, report.getTriggerCount());
        assertEquals(RiskLevel.NONE, report.getRiskLevel());
        assertNotNull(report.getNotesExtracts());
        assertTrue(report.getNotesExtracts().isEmpty());
    }

    @Test
    void riskPatientMore30yrsTest() {
        Long patientId = 1L;
        PatientDto p = patient(patientId, "Josephine", "Serra", "F", LocalDate.now().minusYears(45)); // age > 30
        when(patientClient.getPatientById(patientId)).thenReturn(p);

        List<NoteDto> notes = List.of(
                note("patient fumeur"),
                note("POids en hausse"),
                note("cholesterol élevé"),
                note(null)
        );
        when(noteClient.getNotesForPatient(patientId)).thenReturn(notes);

        RiskReportDto report = riskService.assessRisk(patientId);

        assertEquals(3, report.getTriggerCount());
        assertEquals(RiskLevel.BORDERLINE, report.getRiskLevel());
        assertEquals(4, report.getNotesExtracts().size());
    }

    @Test
    void riskPatientless50yrsTest() {
        Long patientId = 2L;
        PatientDto p = patient(patientId, "Bob", "Marley", "M", LocalDate.now().minusYears(50));
        when(patientClient.getPatientById(patientId)).thenReturn(p);

        List<NoteDto> notes = List.of(
                note("HEMOGLOBINE A1C"),
                note("MICROALBUMINE"),
                note("TAILLE"),
                note("POIDS"),
                note("ANORMAL"),
                note("VERTIGES")
        );
        when(noteClient.getNotesForPatient(patientId)).thenReturn(notes);

        RiskReportDto report = riskService.assessRisk(patientId);

        assertEquals(6, report.getTriggerCount());
        assertEquals(RiskLevel.INDANGER, report.getRiskLevel());
    }

    @Test
    void riskLess60YrsTest() {
        Long patientId = 3L;
        PatientDto p = patient(patientId, "Alice", "Saade", "F", LocalDate.now().minusYears(60));
        when(patientClient.getPatientById(patientId)).thenReturn(p);

        List<NoteDto> notes = List.of(
                note("HEMOGLOBINE A1C"),
                note("MICROALBUMINE"),
                note("TAILLE"),
                note("POIDS"),
                note("FUMEUSE"),
                note("CHOLESTEROL"),
                note("REACTION"),
                note("ANTICORPS")
        );
        when(noteClient.getNotesForPatient(patientId)).thenReturn(notes);

        RiskReportDto report = riskService.assessRisk(patientId);

        assertEquals(8, report.getTriggerCount());
        assertEquals(RiskLevel.EARLYONSET, report.getRiskLevel());
    }

    @Test
    void riskLess25YrsMaleTest() {
        Long patientId = 4L;
        PatientDto p = patient(patientId, "JB", "Gros", "M", LocalDate.now().minusYears(25));
        when(patientClient.getPatientById(patientId)).thenReturn(p);

        List<NoteDto> notes = List.of(
                note("FUMEUR"),
                note("POIDS"),
                note("VERTIGES")
        );
        when(noteClient.getNotesForPatient(patientId)).thenReturn(notes);

        RiskReportDto report = riskService.assessRisk(patientId);

        assertEquals(3, report.getTriggerCount());
        assertEquals(RiskLevel.INDANGER, report.getRiskLevel());
    }

    @Test
    void riskLess29YrsMaleTest() {
        Long patientId = 5L;
        PatientDto p = patient(patientId, "Antoine", "Dupont", "M", LocalDate.now().minusYears(29));
        when(patientClient.getPatientById(patientId)).thenReturn(p);

        List<NoteDto> notes = List.of(
                note("FUMEUR"),
                note("POIDS"),
                note("VERTIGES"),
                note("REACTION"),
                note("ANTICORPS")
        );
        when(noteClient.getNotesForPatient(patientId)).thenReturn(notes);

        RiskReportDto report = riskService.assessRisk(patientId);

        assertEquals(5, report.getTriggerCount());
        assertEquals(RiskLevel.EARLYONSET, report.getRiskLevel());
    }

    @Test
    void riskLess20YrsFemaleTest() {
        Long patientId = 6L;
        PatientDto p = patient(patientId, "Eva", "Longoria", "F", LocalDate.now().minusYears(20));
        when(patientClient.getPatientById(patientId)).thenReturn(p);

        List<NoteDto> notes = List.of(
                note("FUMEUSE"),
                note("POIDS"),
                note("VERTIGES"),
                note("CHOLESTEROL")
        );
        when(noteClient.getNotesForPatient(patientId)).thenReturn(notes);

        RiskReportDto report = riskService.assessRisk(patientId);

        assertEquals(4, report.getTriggerCount());
        assertEquals(RiskLevel.INDANGER, report.getRiskLevel());
    }

    @Test
    void riskLess21YrsFemaleTest() {
        Long patientId = 7L;
        PatientDto p = patient(patientId, "Christina", "Sharapova", "F", LocalDate.now().minusYears(21));
        when(patientClient.getPatientById(patientId)).thenReturn(p);

        List<NoteDto> notes = List.of(
                note("HEMOGLOBINE A1C"),
                note("MICROALBUMINE"),
                note("TAILLE"),
                note("POIDS"),
                note("FUMEUSE"),
                note("CHOLESTEROL"),
                note("VERTIGES")
        );
        when(noteClient.getNotesForPatient(patientId)).thenReturn(notes);

        RiskReportDto report = riskService.assessRisk(patientId);

        assertEquals(7, report.getTriggerCount());
        assertEquals(RiskLevel.EARLYONSET, report.getRiskLevel());
    }

    @Test
    void riskLess22YrsFemaleTest() {
        Long patientId = 8L;
        PatientDto p = patient(patientId, "Patricia", "Montgibert", "F", LocalDate.now().minusYears(22));
        when(patientClient.getPatientById(patientId)).thenReturn(p);

        List<NoteDto> notes = List.of(
                note("FUMEUR"),
                note("POIDS"),
                note("VERTIGES"),
                note("CHOLESTEROL"),
                note("ANORMAL"),
                note("REACTION"),
                note("ANTICORPS")
        );
        when(noteClient.getNotesForPatient(patientId)).thenReturn(notes);

        RiskReportDto report = riskService.assessRisk(patientId);

        assertEquals(RiskLevel.EARLYONSET, report.getRiskLevel());
    }
}
