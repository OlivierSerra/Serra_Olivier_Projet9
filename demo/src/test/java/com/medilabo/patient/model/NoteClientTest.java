package com.medilabo.patient.model;

import com.medilabo.patient.dto.NoteDto;
import com.medilabo.patient.service.NoteClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteClientTest {

    private RestTemplate restTemplate;
    private NoteClient noteClient;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        // on force une URL stable pour les tests
        noteClient = new NoteClient(restTemplate, "http://notes:8082");
    }

    @Test
    void getNotesForPatientNoPatient() {
        Long patientId = 10L;
        String expectedUrl = "http://notes:8082/notes/patient/10";

        when(restTemplate.getForObject(expectedUrl, NoteDto[].class)).thenReturn(null);

        List<NoteDto> result = noteClient.getNotesForPatient(patientId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(restTemplate).getForObject(expectedUrl, NoteDto[].class);
    }

    @Test
    void getNotesForPatient() {
        Long patientId = 5L;
        String expectedUrl = "http://notes:8082/notes/patient/5";

        NoteDto n1 = new NoteDto();
        NoteDto n2 = new NoteDto();
        when(restTemplate.getForObject(expectedUrl, NoteDto[].class)).thenReturn(new NoteDto[]{n1, n2});

        List<NoteDto> result = noteClient.getNotesForPatient(patientId);

        assertEquals(2, result.size());
        assertSame(n1, result.get(0));
        assertSame(n2, result.get(1));
        verify(restTemplate).getForObject(expectedUrl, NoteDto[].class);
    }

}
