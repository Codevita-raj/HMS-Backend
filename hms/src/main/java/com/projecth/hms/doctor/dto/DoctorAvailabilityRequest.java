package com.projecth.hms.doctor.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorAvailabilityRequest {

    private Long doctorId;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer slotDurationMinutes;

    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;
}

