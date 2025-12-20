package com.projecth.hms.patient.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_medical_profile")
@Getter
@Setter
public class PatientMedicalProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long patientId;

    private String bloodGroup;
    private Double height;   // cm
    private Double weight;   // kg

    @Column(length = 1000)
    private String allergies;

    @Column(length = 1000)
    private String chronicConditions;

    @Column(length = 1000)
    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

