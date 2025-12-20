package com.projecth.hms.doctor.entity;

import com.projecth.hms.shared.enums.SlotStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "doctor_slot")
@Getter
@Setter
public class DoctorSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Domain reference
    @Column(nullable = false)
    private Long doctorId;

    @Column(nullable = false)
    private LocalDate slotDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SlotStatus status; // AVAILABLE / BOOKED / BLOCKED

    // Link to availability window
    @Column(nullable = false)
    private Long availabilityId;

    private LocalDateTime createdAt;
}

