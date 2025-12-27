package com.medilabo.notes.repository;

import com.medilabo.notes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * permet de faire fonctionner le CRUD
 * */
public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByPatientId(Long patientId);
}
