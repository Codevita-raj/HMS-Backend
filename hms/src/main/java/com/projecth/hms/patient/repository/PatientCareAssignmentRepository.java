package com.projecth.hms.patient.repository;

import com.projecth.hms.patient.entity.PatientCareAssignment;
import com.projecth.hms.shared.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientCareAssignmentRepository extends JpaRepository<PatientCareAssignment,Long> {
    Optional<PatientCareAssignment>
    findByAdmissionIdAndRoleAndActiveTrue(Long admissionId, Role role);

    List<PatientCareAssignment> findByAdmissionId(Long admissionId);

    List<PatientCareAssignment> findByAdmissionIdAndActiveTrue(Long admissionId);
}
