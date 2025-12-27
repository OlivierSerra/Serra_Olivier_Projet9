package com.medilabo.notes.controller;

import com.medilabo.notes.controller.NoteViewController;
import com.medilabo.notes.model.Note;
import com.medilabo.notes.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NoteViewController.class)
class NoteViewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    NoteService noteService;

    @Test
    void showNotesPatientTest() throws Exception {
        when(noteService.getNotes(1L)).thenReturn(List.of(new Note(), new Note()));

        mockMvc.perform(get("/notes/ui/patient/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("note-liste"))
                .andExpect(model().attributeExists("patientId"))
                .andExpect(model().attributeExists("notes"))
                .andExpect(model().attribute("patientId", 1L));

        verify(noteService).getNotes(1L);
    }

    @Test
    void showFormulaireAddPatientTest() throws Exception {
        mockMvc.perform(get("/notes/ui/ajouter/7"))
                .andExpect(status().isOk())
                .andExpect(view().name("note-ajouter"))
                .andExpect(model().attributeExists("patientId"))
                .andExpect(model().attributeExists("note"))
                .andExpect(model().attribute("patientId", 7L));

        //verifyNoInteractions(noteService);
    }

    @Test
    void showFormulaireModifyTest() throws Exception {
        when(noteService.getNoteById("abc")).thenReturn(null);

        mockMvc.perform(get("/notes/ui/modifier/abc"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/ui/liste"));

        verify(noteService).getNoteById("abc");
        verifyNoMoreInteractions(noteService);
    }

    @Test
    void showFormModifyExistingTest() throws Exception {
        Note n = new Note();
        n.setId("abc");
        n.setPatientId(3L);
        when(noteService.getNoteById("abc")).thenReturn(n);

        mockMvc.perform(get("/notes/ui/modifier/abc"))
                .andExpect(status().isOk())
                .andExpect(view().name("note-modifier"))
                .andExpect(model().attributeExists("patientId"))
                .andExpect(model().attributeExists("note"))
                .andExpect(model().attribute("patientId", 3L));

        verify(noteService).getNoteById("abc");
    }
}
