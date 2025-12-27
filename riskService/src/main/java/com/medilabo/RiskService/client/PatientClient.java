package com.medilabo.RiskService.client;

import com.medilabo.RiskService.dto.PatientDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
/**
 *communique avec sercice patient
 * */
@Component
public class PatientClient {
    //appels HTTP
    private final RestTemplate restTemplate;

    //url micorservice patient
    private static final String BASE_URL = "http://patientservice:8081/patients";

    //injection du microservoce
    public PatientClient(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    // faire remonteer les info patient en fct ID
    public PatientDto getPatientById(Long id) {
        return restTemplate.getForObject(BASE_URL + "/" + id, PatientDto.class);
    }
}
