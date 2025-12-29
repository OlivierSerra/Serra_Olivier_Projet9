package com.medilabo.patient.service;

import com.medilabo.patient.model.PatientModel;
import com.medilabo.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 *
 * gère les CRUD entre le repository et controller - fait le lien
 * */
@Service
public class PatientService {
    //injection d'un répository
    private PatientRepository patientRepository;

    //injection des dépendances
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    //
    public List<PatientModel> findAll() {

        return patientRepository.findAll();
    }

    public PatientModel findById(Long id) {

        return patientRepository.findById(id).orElse(null);
    }

    public PatientModel findByNames(String lastName, String firstName) {
        return patientRepository
                .findByLastNameAndFirstName(lastName, firstName)
                .orElse(null);
    }

    public PatientModel save(PatientModel patient) {

        return patientRepository.save(patient);
    }

    public void deleteById(Long id) {

        patientRepository.deleteById(id);
    }
}
