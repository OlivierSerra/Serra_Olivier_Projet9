package com.openclassroom.frontService.controller;


import com.openclassroom.frontService.dto.NoteDto;
import com.openclassroom.frontService.dto.PatientDto;
import com.openclassroom.frontService.dto.RiskReportDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Controller
public class FrontRoutesController {

    private final RestTemplate restTemplate;

    public FrontRoutesController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/patients/ui/liste")
    public String listePatients(Model model) {

        com.openclassroom.frontService.dto.PatientDto[] patients = restTemplate.getForObject(
                "http://patientservice:8081/patients",
                com.openclassroom.frontService.dto.PatientDto[].class
        );

        model.addAttribute("patients", Arrays.asList(patients));
        return "patients-liste";
    }
     //notes
    @GetMapping("/notes/ui/choisir-patient")
    public String choisirPatientNotesForm() {
        return "choisir-patient-notes";
    }

    @PostMapping("/notes/ui/choisir-patient")
    public String redirectNotes(@RequestParam Long patientId) {
        return "redirect:/notes/ui/patient/" + patientId;
    }

    //risques
    @GetMapping("/risk/ui/choisir-patient")
    public String choisirPatientRisqueForm() {
        return "choisir-patient-risque";
    }

    @PostMapping("/risk/ui/choisir-patient")
    public String redirectRisque(@RequestParam Long patientId) {
        return "redirect:/patients/ui/detail/" + patientId; // ou /risk/{id} selon ton écran
    }
}
