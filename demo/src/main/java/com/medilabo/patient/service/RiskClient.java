package com.medilabo.patient.service;


import com.medilabo.patient.dto.RiskReportDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * commuinique avec le service Risk
 * */
@Service
public class RiskClient {
    //appel du service risque
    private final RestTemplate restTemplate;
    //appel de l URL de la
    private final String riskServiceBaseUrl;

    //injection des dépendances
    public RiskClient(RestTemplate restTemplate,
                      @Value("${risk.service.url:http://localhost:8083}") String riskServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.riskServiceBaseUrl = riskServiceBaseUrl;
    }

    //fait remonter le rapport de risque médical en fct du patient
    public RiskReportDto getRiskForPatient(Long patientId){
        String url = riskServiceBaseUrl + "/risk/" + patientId;
        return restTemplate.getForObject(url, RiskReportDto.class);
    }
}
