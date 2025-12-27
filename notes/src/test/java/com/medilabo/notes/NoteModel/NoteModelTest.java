package com.medilabo.notes.NoteModel;

import com.medilabo.notes.model.Note;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteModelTest {

    @Test
    void noEnrNoteTEst() {
        Note note = new Note();

        assertNotNull(note);
        assertNull(note.getId());
        assertNull(note.getPatientId());
        assertNull(note.getPatient());
        assertNull(note.getNote());
    }

    @Test
    void concordanceNotePatient() {
        Long patientId = 12L;
        String patient = "dédé Serra";
        String text = "Patient smokes.";

        Note note = new Note(patientId, patient, text);

        assertNull(note.getId(), "Id null");
        assertEquals(patientId, note.getPatientId());
        assertEquals(patient, note.getPatient());
        assertEquals(text, note.getNote());
    }

    @Test
    void EnrDonneesNote() {
        Note note = new Note();

        note.setId("123");
        note.setPatientId(99L);
        note.setPatient("Sylvie Leroux");
        note.setNote("covid.");

        assertEquals("123", note.getId());
        assertEquals(99L, note.getPatientId());
        assertEquals("Sylvie Leroux", note.getPatient());
        assertEquals("covid.", note.getNote());
    }

    @Test
    void noEnrDataIfull() {
        Note note = new Note(1L, "Denise", "Hello");

        note.setPatientId(null);
        note.setPatient(null);
        note.setNote(null);

        assertNull(note.getPatientId());
        assertNull(note.getPatient());
        assertNull(note.getNote());
    }
}
