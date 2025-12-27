package com.medilabo.patient.controller;

import com.medilabo.patient.service.RiskClient;
import com.medilabo.patient.dto.RiskReportDto;
import com.medilabo.patient.model.PatientModel;
import com.medilabo.patient.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * gère les vues des patients
 * */
@Controller
@RequestMapping("/patients/ui")
public class PatientViewController {

    private final PatientService patientService;
    private final RiskClient riskClient;
    //injection des dépendances
    public PatientViewController(PatientService patientService, RiskClient riskClient) {
        this.patientService = patientService;
        this.riskClient = riskClient;
    }

    // =========================
    // LISTE
    // affiche liste patients
    // =========================
    @GetMapping("/liste")
    public String afficherListePatients(Model model) {
        List<PatientModel> patients = patientService.findAll();
        //affcihe la list patient
        model.addAttribute("patients", patients);
        return "patients-liste";
    }
    // =========================
    // AJOUTER
    //formulaire ajout +  travail sur un nv objet PAtient
    // =========================
    @GetMapping("/ajouter")
    public String afficherFormAjout(Model model) {
        model.addAttribute("patient", new PatientModel());
        return "patient-ajouter";
    }

    @PostMapping("/ajouter")
    public String traiterAjout(@ModelAttribute("patient") PatientModel patient) {
        //save new patient
        PatientModel saved = patientService.save(patient);
        //redirection sur le bon endpoint
        return "redirect:/patients/ui/liste";
    }

    // =========================
    // MODIFIER
    //affiche formulaire modification pour un patient
    // =========================
    @GetMapping("/modifier/{id}")
    public String afficherFormModification(@PathVariable Long id, Model model) {
        PatientModel patient = patientService.findById(id);
        // si pas patient, revient à la liste
        if (patient == null) {
            return "redirect:/patients/ui/liste";
        }
        model.addAttribute("patient", patient);
        return "patient-modifier";
    }
    /**
     * injection des modif dans fiche patient
     * */
    @PostMapping("/modifier/{id}")
    public String traiterModification(@PathVariable Long id,
                                      @ModelAttribute("patient") PatientModel patient) {
        patient.setId(id);
        patientService.save(patient);
        //redirection vers liste patient
        return "redirect:/patients/ui/liste";
    }

    // =========================
    // SUPPRIMER
    //affiche page suppression
    // =========================
    @GetMapping("/supprimer/{id}")
    public String confirmerSuppression(@PathVariable Long id, Model model) {
        PatientModel patient = patientService.findById(id);
        if (patient == null) {
            return "redirect:/patients/ui/liste";
        }
        model.addAttribute("patient", patient);
        return "patient-supprimer";
    }

    @PostMapping("/supprimer/{id}")
    public String traiterSuppression(@PathVariable Long id) {
        patientService.deleteById(id);
        return "redirect:/patients/ui/liste";
    }

    //Afficher le détail des patients

    @GetMapping("/detail/{id}")
    public String afficherDetailPatient(@PathVariable Long id, Model model) {
        PatientModel patient = patientService.findById(id);
        if (patient == null) {
            return "redirect:/patients/ui/liste";
        }
        //délégation au service risque
        RiskReportDto risk = riskClient.getRiskForPatient(id);

        model.addAttribute("patient", patient);
        model.addAttribute("risk", risk);

        return "patient-detail";
    }
}
