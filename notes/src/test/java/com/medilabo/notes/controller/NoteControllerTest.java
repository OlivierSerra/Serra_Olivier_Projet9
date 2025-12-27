package com.medilabo.notes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medilabo.notes.model.Note;
import com.medilabo.notes.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteController.class)
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NoteService noteService;

    @Test
    void getAllNotesTest() throws Exception {
        Note n1 = new Note(); 
        n1.setId("1");
        Note n2 = new Note(); 
        n2.setId("2");

        when(noteService.getAllNotes()).thenReturn(List.of(n1, n2));

        mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));

        verify(noteService).getAllNotes();
    }

    @Test
    void getNotesByPatientTest() throws Exception {
        when(noteService.getNotes(5L)).thenReturn(List.of(new Note()));

        mockMvc.perform(get("/notes/patient/5"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(noteService).getNotes(5L);
    }

    @Test
    void addNoteTest() throws Exception {
        Note input = new Note();
        input.setPatientId(7L);
        input.setNote("test");

        Note created = new Note();
        created.setId("a");
        created.setPatientId(7L);
        created.setNote("test");

        when(noteService.addNote(any(Note.class))).thenReturn(created);

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/notes/a"))
                .andExpect(jsonPath("$.id").value("a"));

        verify(noteService).addNote(any(Note.class));
    }

    @Test
    void updateNoteTest() throws Exception {
        Note updated = new Note();
        updated.setId("a");
        updated.setNote("updated");

        when(noteService.updateNote(eq("a"), any(Note.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/notes/a")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("a"));

        verify(noteService).updateNote(eq("a"), any(Note.class));
    }

    @Test
    void deleteNoteTest() throws Exception {
        mockMvc.perform(delete("/notes/a"))
                .andExpect(status().isNoContent());

        verify(noteService).deleteNote("a");
    }
}
