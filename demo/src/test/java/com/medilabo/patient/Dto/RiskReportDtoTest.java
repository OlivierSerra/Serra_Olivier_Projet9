package com.medilabo.patient.Dto;

import com.medilabo.patient.dto.RiskReportDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RiskReportDtoTest {

    @Test
    void stkDonnees() {
        RiskReportDto dto = new RiskReportDto();

        dto.setPatientId(42L);
        dto.setLastName("Serra");
        dto.setFirstName("pepe");
        dto.setBirthDate("1926-01-05");
        dto.setGender("M");
        dto.setRiskLevel("INDANGER");
        dto.setTriggerCount(6);

        assertEquals(42L, dto.getPatientId());
        assertEquals("Serra", dto.getLastName());
        assertEquals("pepe", dto.getFirstName());
        assertEquals("1926-01-05",dto.getBirthDate());
        assertEquals("M", dto.getGender());
        assertEquals("INDANGER", dto.getRiskLevel());
        assertEquals(6, dto.getTriggerCount());
    }

    @Test
    void NullValuestest() {
        RiskReportDto dto = new RiskReportDto();

        dto.setPatientId(null);
        dto.setLastName(null);
        dto.setFirstName(null);
        dto.setBirthDate(null);
        dto.setGender(null);
        dto.setRiskLevel(null);
        dto.setTriggerCount(null);

        assertNull(dto.getPatientId());
        assertNull(dto.getLastName());
        assertNull(dto.getFirstName());
        assertNull(dto.getBirthDate());
        assertNull(dto.getGender());
        assertNull(dto.getRiskLevel());
        assertNull(dto.getTriggerCount());
    }

    @Test
    void CreateEmptyRisk() {
        RiskReportDto dto = new RiskReportDto();

        assertNull(dto.getPatientId());
        assertNull(dto.getLastName());
        assertNull(dto.getFirstName());
        assertNull(dto.getBirthDate());
        assertNull(dto.getGender());
        assertNull(dto.getRiskLevel());
        assertNull(dto.getTriggerCount());
    }
}
