package com.medilabo.notes.service;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.repository.NoteRepository;
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
class NoteServiceTest {

    @Mock
    private NoteRepository repo;

    private NoteService noteService;

    @BeforeEach
    void setup() {
        noteService = new NoteService(repo);
    }

    @Test
    void getNotesWithPatientIdTest() {
        when(repo.findByPatientId(1L)).thenReturn(List.of(new Note(), new Note()));

        List<Note> result = noteService.getNotes(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repo).findByPatientId(1L);
        //verifyNoMoreInteractions(repo);
    }

    @Test
    void addNotePatientTest() {
        Note note = new Note();
        when(repo.save(note)).thenReturn(note);

        Note result = noteService.addNote(note);

        assertSame(note, result);
        verify(repo).save(note);
        //verifyNoMoreInteractions(repo);
    }

    @Test
    void getAllNotesTest() {
        when(repo.findAll()).thenReturn(List.of(new Note()));

        List<Note> result = noteService.getAllNotes();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repo).findAll();
        //verifyNoMoreInteractions(repo);
    }

    @Test
    void deleteNoteWithIdTest() {
        when(repo.existsById("88")).thenReturn(true);

        noteService.deleteNote("88");

        verify(repo).existsById("88");
        verify(repo).deleteById("88");
        //verifyNoMoreInteractions(repo);
    }

    @Test
    void deleteNoteTest() {
        when(repo.existsById(null)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> noteService.deleteNote(null));

        assertTrue(ex.getMessage().contains("Note not found with id"));
        verify(repo).existsById(null);
        verify(repo, never()).deleteById(anyString());
        //verifyNoMoreInteractions(repo);
    }

    @Test
    void saveNotesTest() {
        Note note = new Note();
        when(repo.save(note)).thenReturn(note);

        Note result = noteService.save(note);

        assertSame(note, result);
        verify(repo).save(note);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void getNoteByIdTest() {
        Note note = new Note();
        when(repo.findById("abc")).thenReturn(Optional.of(note));

        Note result = noteService.getNoteById("abc");

        assertSame(note, result);
        verify(repo).findById("abc");
        verifyNoMoreInteractions(repo);
    }

    @Test
    void getNoteByNoId() {
        when(repo.findById(null)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> noteService.getNoteById(null));

        assertTrue(ex.getMessage().contains("Note non trouvée"));
        verify(repo).findById(null);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void updateNoteTest() {
        Note existing = new Note();
        existing.setId("abc");
        existing.setPatientId(1L);
        existing.setPatient("Old patient");
        existing.setNote("Old note");

        Note updated = new Note();
        updated.setPatientId(2L);
        updated.setPatient("New patient");
        updated.setNote("New note");

        when(repo.findById("abc")).thenReturn(Optional.of(existing));
        when(repo.save(existing)).thenReturn(existing);

        Note result = noteService.updateNote("abc", updated);

        assertSame(existing, result);
        assertEquals(2L, existing.getPatientId());
        assertEquals("New patient", existing.getPatient());
        assertEquals("New note", existing.getNote());

        verify(repo).findById("abc");
        verify(repo).save(existing);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void updateNoteWithNoIdTest() {
        when(repo.findById(null)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> noteService.updateNote(null, new Note()));

        assertTrue(ex.getMessage().contains("Note not found"));
        verify(repo).findById(null);
        verify(repo, never()).save(any(Note.class));
        verifyNoMoreInteractions(repo);
    }
}
