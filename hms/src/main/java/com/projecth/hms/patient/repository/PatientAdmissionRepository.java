package com.projecth.hms.patient.repository;

import com.projecth.hms.patient.entity.Patient;
import com.projecth.hms.patient.entity.PatientAdmission;
import com.projecth.hms.patient.entity.PatientCareAssignment;
import com.projecth.hms.shared.enums.AdmissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientAdmissionRepository extends JpaRepository<PatientAdmission,Long> {


    List<PatientAdmission> findByPatientId(Long patientId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM PatientAdmission a " +
            "WHERE a.patientId = :patientId AND a.status IN :activeStatuses")
    boolean existsActiveAdmission(@Param("patientId") Long patientId,
                                  @Param("activeStatuses") List<AdmissionStatus> activeStatuses);

    List<PatientCareAssignment> findByAdmissionId(Long admissionId);
    Optional<PatientAdmission> findFirstByPatientIdAndStatusIn(
            Long patientId,
            List<AdmissionStatus> statuses
    );
}
