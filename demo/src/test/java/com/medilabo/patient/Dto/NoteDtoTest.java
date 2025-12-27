package com.medilabo.patient.Dto;

import com.medilabo.patient.dto.NoteDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteDtoTest {

    @Test
    void RegistrationDataPatientTest() {
        NoteDto dto = new NoteDto();

        dto.setId("n1");
        dto.setPatientId("p1");
        dto.setPatient("Serra dédé");
        dto.setNote("Patient est fumeur.");

        assertEquals("n1", dto.getId());
        assertEquals("p1", dto.getPatientId());
        assertEquals("Serra dédé", dto.getPatient());
        assertEquals("Patient est fumeur.", dto.getNote());
    }

    @Test
    void enrDonnesPatientIfEmpty() {
        NoteDto dto = new NoteDto();

        dto.setId(null);
        dto.setPatientId(null);
        dto.setPatient(null);
        dto.setNote(null);

        assertNull(dto.getId());
        assertNull(dto.getPatientId());
        assertNull(dto.getPatient());
        assertNull(dto.getNote());
    }
}
