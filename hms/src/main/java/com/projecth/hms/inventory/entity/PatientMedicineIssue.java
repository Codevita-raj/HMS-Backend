package com.projecth.hms.inventory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_medicine_issue")
@Getter
@Setter
public class PatientMedicineIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private Long admissionId;

    private Long itemId;
    private Long locationId;

    private Integer quantity;

    private String issuedBy; // nurse/pharmacist
    private String remarks;

    private LocalDateTime issuedAt;

    private String createdBy;
}

