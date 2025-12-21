package com.projecth.hms.inventory.repository;

import com.projecth.hms.inventory.entity.PatientMedicineIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientMedicineIssueRepository
        extends JpaRepository<PatientMedicineIssue, Long> {

    List<PatientMedicineIssue> findByAdmissionId(Long admissionId);

    List<PatientMedicineIssue> findByPatientId(Long patientId);
}

