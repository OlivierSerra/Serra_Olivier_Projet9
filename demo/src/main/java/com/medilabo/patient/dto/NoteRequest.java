package com.medilabo.patient.dto;

import org.springframework.stereotype.Component;

//sert à importer (ou lier) le contenu de la note dans le microservice patient
//info qure le sys reçoit du clt
public class NoteRequest {
    private Long patientId;
    private String content;

    //Constructeurs
    public NoteRequest() {}

    //getters & setters
    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
