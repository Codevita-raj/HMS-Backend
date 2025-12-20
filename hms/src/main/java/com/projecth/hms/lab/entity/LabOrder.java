package com.projecth.hms.lab.entity;

import com.projecth.hms.shared.enums.LabOrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "lab_order")
@Getter
@Setter
public class LabOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private Long admissionId;
    private Long doctorId;

    @Enumerated(EnumType.STRING)
    private LabOrderStatus status;

    private LocalDateTime orderedAt;

    private LocalDateTime createdAt;
    private String createdBy;

}

