package com.projecth.hms.lab.entity;

import com.projecth.hms.shared.enums.LabSampleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "lab_order_item")
@Getter
@Setter
public class LabOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long labOrderId;

    private Long labTestId;

    @Enumerated(EnumType.STRING)
    @Column(name = "sample_status", length = 30)
    private LabSampleStatus sampleStatus;


    private String resultValue;
    private String resultRemarks;

    private LocalDateTime sampleCollectedAt;
    private LocalDateTime resultUpdatedAt;
}
