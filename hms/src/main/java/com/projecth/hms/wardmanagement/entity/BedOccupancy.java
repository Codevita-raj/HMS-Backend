package com.projecth.hms.wardmanagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "bed_occupancy")
@Getter
@Setter
public class BedOccupancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bedId;
    private Long patientId;
    private Long admissionId;
    private String remarks;

    private LocalDateTime occupiedFrom;
    private LocalDateTime occupiedTill;
}

