package com.medilabo.patient.Dto;

import com.medilabo.patient.dto.PatientDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PatientDtoTest {

    @Test
    void enr() {
        PatientDto dto = new PatientDto();

        dto.setId("1");
        dto.setPatientId("100");
        dto.setPatient("dédé Serra");
        dto.setNote("Patient fumeur");

        assertEquals("1", dto.getId());
        assertEquals("100", dto.getPatientId());
        assertEquals("dédé Serra", dto.getPatient());
        assertEquals("Patient fumeur", dto.getNote());
    }

    @Test
    void noEnrPatientWithoutData() {
        PatientDto dto = new PatientDto();

        dto.setId(null);
        dto.setPatientId(null);
        dto.setPatient(null);
        dto.setNote(null);

        assertNull(dto.getId());
        assertNull(dto.getPatientId());
        assertNull(dto.getPatient());
        assertNull(dto.getNote());
    }

    @Test
    void noEnrIfNoDataPatientTest() {
        PatientDto dto = new PatientDto();

        assertNull(dto.getId());
        assertNull(dto.getPatientId());
        assertNull(dto.getPatient());
        assertNull(dto.getNote());
    }
}
