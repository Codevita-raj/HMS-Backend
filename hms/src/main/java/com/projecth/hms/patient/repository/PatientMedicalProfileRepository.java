package com.projecth.hms.patient.repository;

import com.projecth.hms.patient.entity.PatientMedicalProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientMedicalProfileRepository extends JpaRepository<PatientMedicalProfile,Long > {
    Optional<PatientMedicalProfile> findByPatientId(Long patientId);
}
