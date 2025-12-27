package com.medilabo.notes.controller;

import com.medilabo.notes.model.Note;
import com.medilabo.notes.service.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur en charge des vues du service notes - CRUD du service notes
 */
@Controller
@RequestMapping("/notes/ui")
public class NoteViewController {

    private final NoteService noteService;

    public NoteViewController(NoteService noteService) {
        this.noteService = noteService;
    }

    // Liste des notes pour un patient
    @GetMapping("/patient/{patientId}")
    public String afficherNotesPatient(@PathVariable Long patientId, Model model) {
        List<Note> notes = noteService.getNotes(patientId);

        String patientName = null;
        if (notes != null && !notes.isEmpty()) {
            patientName = notes.get(0).getPatient();
        }

        model.addAttribute("patientId", patientId);
        model.addAttribute("patientName", patientName);
        model.addAttribute("notes", notes);

        return "note-liste";
    }

    // Evite les erreurs 404 si pas d'ID => redirection vers liste patients
    @GetMapping({"/patient", "/patient/"})
    public String patientSansId() {
        return "redirect:/patients/ui/liste";
    }

    // Afficher formulaire d'ajout d'une note pour patient
    @GetMapping("/ajouter/{patientId}")
    public String afficherFormAjout(@PathVariable Long patientId, Model model) {
        Note note = new Note();
        note.setPatientId(patientId);

        model.addAttribute("patientId", patientId);
        model.addAttribute("note", note);

        return "note-ajouter";
    }

    // Envoi du formulaire d'ajout d'une note
    @PostMapping("/ajouter/{patientId}")
    public String traiterAjout(@PathVariable Long patientId,
                               @ModelAttribute("note") Note note) {

        // Sécurité: on force l'id patient depuis l'URL (et pas depuis le navigateur)
        note.setPatientId(patientId);

        // Ici, note.getPatient() vient du champ saisi dans le formulaire
        // note.getNote() vient du textarea
        noteService.save(note);

        return "redirect:/notes/ui/patient/" + patientId;
    }

    // Afficher formulaire de modification
    @GetMapping("/modifier/{id}")
    public String afficherFormModifier(@PathVariable String id, Model model) {
        Note note = noteService.getNoteById(id);
        if (note == null) {
            return "redirect:/patients/ui/liste";
        }

        model.addAttribute("patientId", note.getPatientId());
        model.addAttribute("note", note);

        return "note-modifier";
    }

    // Traitement du formulaire de modification
    @PostMapping("/modifier/{id}")
    public String traiterModification(@PathVariable String id,
                                      @ModelAttribute("note") Note form) {

        Note existing = noteService.getNoteById(id);
        if (existing == null) {
            return "redirect:/patients/ui/liste";
        }

        // Mise à jour
        existing.setNote(form.getNote());

        existing.setPatient(form.getPatient());

        noteService.save(existing);

        return "redirect:/notes/ui/patient/" + existing.getPatientId();
    }

    // Supprimer une note
    @GetMapping("/supprimer/{id}")
    public String traiterSuppression(@PathVariable String id) {
        Note note = noteService.getNoteById(id);
        if (note == null) {
            return "redirect:/patients/ui/liste";
        }

        Long patientId = note.getPatientId();
        noteService.deleteNote(id);

        return "redirect:/notes/ui/patient/" + patientId;
    }
}
