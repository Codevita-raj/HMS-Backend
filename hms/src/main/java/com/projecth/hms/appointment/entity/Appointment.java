package com.projecth.hms.appointment.entity;

import com.projecth.hms.shared.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;   // domain reference
    private Long doctorId;    // domain reference

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    private String reason;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

