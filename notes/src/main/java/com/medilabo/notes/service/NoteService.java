package com.medilabo.notes.service;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.repository.NoteRepository;
import org.springframework.stereotype.Service;

//import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;
/**
 * génère automatiquement implémentation
 * */
@Service
public class NoteService {

    //permet d'accéde
    private final NoteRepository repo;

    //constructeurs
    public NoteService(NoteRepository repo) {

        this.repo = repo;
    }

    //fait remonter les notes d'un poatient
    public List<Note> getNotes(Long patientId) {
        return repo.findByPatientId(patientId);
    }

    // ajut d'une note
    public Note addNote(Note note) {
        return repo.save(note);
    }

    //renvoi ttes les notes
    public List<Note> getAllNotes() {
        return repo.findAll();
    }

    //supprime une note en fct de l'ID
    public void deleteNote(String id){
        if (!repo.existsById(id)) {
            throw new RuntimeException("Note not found with id " + id);
        }
        repo.deleteById(id);
    }
    //enr la note
    public Note save(Note b){

        return repo.save(b);
    }

    //afficher la note en fct de l'id
    public Note getNoteById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Note non trouvée : " + id));
    }

    //modif la note
    public Note updateNote(String id, Note updated) {
        Note existing = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note not found: " + id));
        existing.setPatientId(updated.getPatientId());
        existing.setPatient(updated.getPatient());
        existing.setNote(updated.getNote());
        repo.save(existing);
        return existing;
    }

}