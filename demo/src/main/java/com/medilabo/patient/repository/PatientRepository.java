    package com.medilabo.patient.repository;

    import com.medilabo.patient.model.PatientModel;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.Optional;

    @Repository
    public interface PatientRepository extends JpaRepository<PatientModel, Long> {
        Optional<PatientModel> findByLastNameAndFirstName(String lastName, String firstName);
    }