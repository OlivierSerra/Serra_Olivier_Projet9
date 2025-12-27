package com.medilabo.patient.service;

import com.medilabo.patient.model.PatientModel;
import com.medilabo.patient.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    private PatientService patientService;

    //création d'un nv patient
    @BeforeEach
    void setup() {
        patientService = new PatientService(patientRepository);
    }

    @Test
    void findAllTest() {
        when(patientRepository.findAll()).thenReturn(List.of());

        List<PatientModel> result = patientService.findAll();

        assertNotNull(result);
        verify(patientRepository).findAll();
        verifyNoMoreInteractions(patientRepository);
    }

    @Test
    void findByIdTest() {
        PatientModel patient = new PatientModel();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        PatientModel result = patientService.findById(1L);

        assertSame(patient, result);
        verify(patientRepository).findById(1L);
        verifyNoMoreInteractions(patientRepository);
    }



    @Test
    void findByNamesTest() {
        PatientModel patient = new PatientModel();
        when(patientRepository.findByLastNameAndFirstName("Serra", "dédé"))
                .thenReturn(Optional.of(patient));

        PatientModel result = patientService.findByNames("Serra", "dédé");

        assertSame(patient, result);
        verify(patientRepository).findByLastNameAndFirstName("Serra", "dédé");
        verifyNoMoreInteractions(patientRepository);
    }


    @Test
    void saveTest() {
        PatientModel patient = new PatientModel();
        when(patientRepository.save(patient)).thenReturn(patient);

        PatientModel saved = patientService.save(patient);

        assertSame(patient, saved);
        verify(patientRepository).save(patient);
        verifyNoMoreInteractions(patientRepository);
    }

    @Test
    void deleteByIdTest() {
        patientService.deleteById(5L);

        verify(patientRepository).deleteById(5L);
        verifyNoMoreInteractions(patientRepository);
    }

}
