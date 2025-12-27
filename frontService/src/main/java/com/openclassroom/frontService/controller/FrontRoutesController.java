package com.openclassroom.frontService.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
