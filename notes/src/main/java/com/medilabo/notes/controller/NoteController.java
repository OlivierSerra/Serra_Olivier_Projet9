package com.medilabo.notes.controller;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * controleur affichant les notes + modif + suppr + creation--
 *affiche les notes
 * */
@RestController
@RequestMapping("/notes")
public class NoteController {

    // gestion des notes
    private final NoteService service;

    //injection du service notes
    public NoteController(NoteService service) {
        this.service = service;
    }

    // fait remonter toutes les notes
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(service.getAllNotes());
    }

    // récupère notes associés à un patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Note>> getNotesByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(service.getNotes(patientId));
    }

    // injecte une nouvelle note
        @PostMapping
        public ResponseEntity<Note> addNote(@RequestBody Note note) {
            Note created = service.addNote(note);
            URI location = URI.create("/notes/" + created.getId());
            return ResponseEntity.created(location).body(created);
        }

    // Met à jour une note existante
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable String id, @RequestBody Note note) {
        Note updated = service.updateNote(id, note);
        return ResponseEntity.ok(updated);
    }

    // suppr note en fct de l'ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        service.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}
