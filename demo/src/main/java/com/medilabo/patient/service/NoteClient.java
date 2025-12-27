    package com.medilabo.patient.service;

    import com.medilabo.patient.dto.NoteDto;
    import com.medilabo.patient.dto.NoteRequest;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;
    import org.springframework.web.client.RestTemplate;

    import java.util.Arrays;
    import java.util.List;

    /**
     * permet de communiquer avec le service Notes
     * couche métier permettant de centraliser les communications
     * puisque patient centralise tous les services en 1 pt sinon aurait dû être dans controleur
     *
     * */
    @Component
    public class NoteClient {

        //RestTamplates pour appeler le microservice "notes"
        private final RestTemplate restTemplate;
        // http://localhost:8081 POUR LES PATIENTS
        //url du service notes - injecté dans configuration
        private final String noteServiceBaseUrl;

     //injection des dépendances
        public NoteClient(RestTemplate restTemplate,
                          //intéreêt pour Docker notes.service.url dans application.properties
                          // donc facile à changer pour le déploiement
                          @Value("${notes.service.url:http://localhost:8082}") String noteServiceBaseUrl) {
            this.restTemplate = restTemplate;
            this.noteServiceBaseUrl = noteServiceBaseUrl;
        }
        //donne les notes par patients
        public List<NoteDto> getNotesForPatient(Long patientId) {
            String url = noteServiceBaseUrl + "/notes/patient/" + patientId;
            NoteDto[] response = restTemplate.getForObject(url, NoteDto[].class);
            //opérateur ternaire permet de s'adapter et d'eviter une exception (nulPointerException)
            return response != null ? Arrays.asList(response) : List.of();
        }
        //ajout de notes
        public NoteDto addNote(Long patientId, String content) {
            NoteRequest request = new NoteRequest();
            request.setPatientId(patientId);
            request.setContent(content);

            String url = noteServiceBaseUrl + "/notes";
            return restTemplate.postForObject(url, request, NoteDto.class);
        }
    }