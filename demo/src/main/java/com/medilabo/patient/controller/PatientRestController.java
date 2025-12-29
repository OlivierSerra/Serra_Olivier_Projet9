    package com.medilabo.patient.controller;

    import com.medilabo.patient.dto.NoteDto;
    import com.medilabo.patient.service.NoteClient;
    import com.medilabo.patient.dto.NoteRequest;
    import com.medilabo.patient.model.PatientModel;

    import com.medilabo.patient.service.PatientService;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    /**
     * Ctrleur affichant les endpoints CRud des patients
     * */

    @RestController
    @RequestMapping("/patients")
    public class PatientRestController {

        private final PatientService patientService;
        private final NoteClient noteClient;

        //ctrleur injection dependaces
        public PatientRestController(PatientService patientService, NoteClient noteClient) {
            this.patientService = patientService;
            this.noteClient = noteClient;
        }

        // =======================
        // LISTE DES PATIENTS
        // retourne la liste des patients
        // =====================
        @GetMapping
        public List<PatientModel> getAllPatients() {
            return patientService.findAll();
        }

        // =======================
        // DÉTAIL D'UN PATIENT
        //retourne les détzails d'un  patients
        // =======================
        @GetMapping("/{id}")
        public ResponseEntity<PatientModel> getPatientById(@PathVariable Long id) {
            PatientModel patient = patientService.findById(id);
            //permet de gérer le cas ou patint abs
            if (patient == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(patient);
        }

        // ===========
        //trouver un patient gràce à son nom
        //=========
        @GetMapping("/search")
        public ResponseEntity<PatientModel> findByNames(@RequestParam String lastName,
                                        @RequestParam String firstName) {
            //recherche par nom et prénom
            PatientModel foundedPatient= patientService.findByNames(lastName, firstName);
            if (foundedPatient == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(foundedPatient);
        }

        // =======================
        // CRÉER UN PATIENT
        // enr une nouvle occurence dans la lisgte des patients
        // =======================
        @PostMapping
        public ResponseEntity<PatientModel> createPatient(@RequestBody PatientModel patient) {
            PatientModel saved = patientService.save(patient);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }

        // ====================
        // METTRE À JOUR UN PATIENT
        // modif des info d'un patient
        // =======================
        @PutMapping("/{id}")
        public ResponseEntity<PatientModel> updatePatient(@PathVariable Long id ,
                                                          @RequestBody PatientModel updated) {

            PatientModel existing = patientService.findById(id);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }

            updated.setId(id);
            PatientModel saved = patientService.save(updated);
            return ResponseEntity.ok(saved);
        }

        // =======================
        // SUPPRIMER UN PATIENT
        // delete un patient
        // ======================
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePatient(@PathVariable Long id ) {
            PatientModel existing = patientService.findById(id);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            }
            patientService.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        // =======================
        // NOTES D'UN PATIENT
        // récup noptes d'un patient
        // =======================
        @GetMapping("/{id}/notes")
        public ResponseEntity<List<NoteDto>> getNotes(@PathVariable Long id ) {
            PatientModel patient = patientService.findById(id);
            if (patient == null) {
                return ResponseEntity.notFound().build();
            }
            //utilise microservice notes
            List<NoteDto> notes = noteClient.getNotesForPatient(id);
            return ResponseEntity.ok(notes);
        }

        // =======================
        // AJOUTER UNE NOTE
        // ajouter une note pour chq patient
        // =======================
        @PostMapping("/{id}/notes")
        public ResponseEntity<NoteDto> addNote(@PathVariable Long id ,
                                            @RequestBody NoteRequest dto) {

            PatientModel patient = patientService.findById(id);
            if (patient == null) {
                return ResponseEntity.notFound().build();
            }
            NoteDto savedNote = noteClient.addNote(dto.getPatientId(), dto.getContent());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
        }

    }
