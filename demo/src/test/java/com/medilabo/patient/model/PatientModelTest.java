package com.medilabo.patient.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PatientModelTest {

    //inspiré de bidListTest
    @Test
    void PatientTest() {
        LocalDate birth = LocalDate.of(1990, 1, 2);

        PatientModel p = new PatientModel(
                "Serra",
                "Olivier",
                birth,
                "M",
                "7 rue Etienne Dolet",
                "0621457896"
        );

        assertEquals("Serra", p.getLastName());
        assertEquals("Olivier", p.getFirstName());
        assertEquals(birth, p.getBirthDate());
        assertEquals("M", p.getGender());
        assertEquals("7 rue Etienne Dolet", p.getAddress());
        assertEquals("0621457896", p.getPhoneNumber());
    }

    @Test
    void patientTestOk() {
        PatientModel p = new PatientModel();
        LocalDate birth = LocalDate.of(1985, 5, 6);

        p.setId(42L);
        p.setLastName("Moeda");
        p.setFirstName("Sylvie");
        p.setBirthDate(birth);
        p.setGender("F");
        p.setAddress("1 rue du Grand Bornand");
        p.setPhoneNumber("0589562341");

        assertEquals(42L, p.getId());
        assertEquals("Moeda", p.getLastName());
        assertEquals("Sylvie", p.getFirstName());
        assertEquals(birth, p.getBirthDate());
        assertEquals("F", p.getGender());
        assertEquals("1 rue du Grand Bornand", p.getAddress());
        assertEquals("0589562341", p.getPhoneNumber());
    }
}
