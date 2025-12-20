package com.projecth.hms.patient.repository;

import com.projecth.hms.patient.entity.PatientEmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientEmergencyContactRepository
        extends JpaRepository<PatientEmergencyContact, Long> {

    List<PatientEmergencyContact> findByPatientId(Long patientId);

    Optional<PatientEmergencyContact> findByPatientIdAndPrimaryContactTrue(Long patientId);

    boolean existsByPatientIdAndPrimaryContactTrue(Long patientId);

//    Optional<PatientEmergencyContact> findByPatientIdAndPrimaryTrue(Long patientId);
}

