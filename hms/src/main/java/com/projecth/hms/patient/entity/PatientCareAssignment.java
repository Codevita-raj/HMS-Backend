package com.projecth.hms.patient.entity;

import com.projecth.hms.shared.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "patient_care_assignment")
public class PatientCareAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long admissionId;
    private Long staffId;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime assignedFrom;
    private LocalDateTime assignedTo;

    private Boolean active;
}
