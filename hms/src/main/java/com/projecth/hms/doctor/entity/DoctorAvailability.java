package com.projecth.hms.doctor.entity;

import com.projecth.hms.shared.enums.AvailabilityStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "doctor_availability")
@Getter
@Setter
public class DoctorAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Domain reference (NO JPA relation)
    @Column(nullable = false)
    private Long doctorId;

    @Column(nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Integer slotDurationMinutes; // eg: 15, 20, 30

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AvailabilityStatus status; // ACTIVE / INACTIVE

    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

