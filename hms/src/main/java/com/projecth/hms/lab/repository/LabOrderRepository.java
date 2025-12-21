package com.projecth.hms.lab.repository;

import com.projecth.hms.lab.entity.LabOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabOrderRepository extends JpaRepository<LabOrder, Long> {
    List<LabOrder> findByPatientId(Long patientId);

    List<LabOrder> findByAdmissionId(Long admissionId);
    List<Long> findIdsByAdmissionId(Long admissionId);

}

