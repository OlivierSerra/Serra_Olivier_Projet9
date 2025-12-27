package com.medilabo.patient.dto;

import java.time.LocalDateTime;

//fornat de données dont riskService a besoin pour fonctionner
public class NoteDto {
    //! id de la note
    private String id;
    //id du patient
    private String patientId;
    //nom du patient
    private String patient;
    //contenu de la note
    private String note;


    public NoteDto() {}

    //getters & setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public String getPatient() {
            return patient;
        }

        public void setPatient(String patient) {
            this.patient = patient;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

}
