package com.projecth.hms.patient.entity;

import com.projecth.hms.shared.enums.AdmissionStatus;
import com.projecth.hms.shared.enums.AdmissionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "patient_admissions")
public class PatientAdmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admissionId;

    @Column(nullable = false)
    private Long patientId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdmissionType admissionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdmissionStatus status;

    @Column(nullable = false)
    private LocalDateTime admissionDate;

    private LocalDateTime dischargeDate;

    @Column(length = 500)
    private String reasonForAdmission;

    @Column(length = 500)
    private String remarks;

    // audit
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

}