package com.projecth.hms.patient.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_emergency_contact")
@Getter
@Setter
public class PatientEmergencyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long patientId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String relation; // Father, Mother, Spouse, Friend

    @Column(nullable = false)
    private String phone;

    private String alternatePhone;

    private Boolean primaryContact = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

