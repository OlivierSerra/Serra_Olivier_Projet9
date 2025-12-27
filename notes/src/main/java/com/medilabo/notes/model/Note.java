package com.medilabo.notes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
/**
 * format des données récoltées à partir de la BDD
 * */
@Document(collection = "notes")
public class Note {

    @Id
    private String id;
    private Long patientId;
    private String patient;
    private String note;

    //======
    //constructeurs
    //==============

    public Note() {
    }

    public Note(Long patientId, String patient, String note) {
        this.patientId = patientId;
        this.patient = patient;
        this.note = note;
    }

    //getters & setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
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