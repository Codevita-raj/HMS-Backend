package com.projecth.hms.appointment.dto;

import com.projecth.hms.shared.enums.AppointmentStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {

    private Long appointmentId;

    private Long patientId;
    private Long doctorId;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    private String reason;

    private AppointmentStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

