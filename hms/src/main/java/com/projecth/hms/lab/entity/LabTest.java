package com.projecth.hms.lab.entity;

import com.projecth.hms.shared.enums.LabTestType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lab_test")
@Getter
@Setter
public class LabTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String testCode;   // e.g. CBC, BS_FASTING

    @Column(nullable = false)
    private String testName;   // e.g. Complete Blood Count

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LabTestType testType;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Boolean active = true;

    private Long departmentId;

    private LocalDateTime createdAt;
    private String createdBy;

    private LocalDateTime updatedAt;
    private String updatedBy;
}

