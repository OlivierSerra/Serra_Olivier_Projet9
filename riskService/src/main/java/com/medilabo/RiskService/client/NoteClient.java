package com.medilabo.RiskService.client;

import com.medilabo.RiskService.dto.NoteDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
/**
 * pas un @service, pas un @repository, @Controller donc @component (composant générique)
 *
 * */
@Component
public class NoteClient {
    // appels HTTP géré par RestTemplate (env +recp derequetes HTTP
    private final RestTemplate restTemplate;

    //url du service notes
    private static final String BASE_URL = "http://noteservice:8082/notes/patient/";

    //injection de dépendances
    public NoteClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
     //récupèrel'ens des notesd'un aptient
    public List<NoteDto> getNotesForPatient(Long patientId) {
        String url = BASE_URL + "/" + patientId;

        ResponseEntity<List<NoteDto>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<NoteDto>>() {}
                );

        return response.getBody();
    }
}
