package com.projecth.hms.patient.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_consent")
@Getter
@Setter
public class PatientConsent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private String consentType;
    // GENERAL, SURGERY, DATA_SHARING, ANESTHESIA

    @Column(nullable = false)
    private Boolean consentGiven;

    private String consentGivenBy;
    // SELF, GUARDIAN

    private String guardianName;

    private LocalDateTime consentDate;

    @Column(length = 1000)
    private String remarks;

    private LocalDateTime createdAt;
}

