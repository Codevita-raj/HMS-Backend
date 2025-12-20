package com.projecth.hms.patient.repository;

import com.projecth.hms.patient.entity.PatientConsent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientConsentRepository extends JpaRepository<PatientConsent,Long> {

    Optional<PatientConsent> findTopByPatientIdOrderByConsentDateDesc(Long patientId);

    List<PatientConsent> findAllByPatientIdOrderByConsentDateDesc(Long patientId);

    Optional<PatientConsent> findByPatientId(Long patientId);

    Optional<PatientConsent>
    findFirstByPatientIdOrderByConsentDateDesc(Long patientId);

}
