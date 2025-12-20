package com.projecth.hms.doctor.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import com.projecth.hms.shared.enums.AvailabilityStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DoctorAvailabilityResponse {

    private Long availabilityId;
    private Long doctorId;

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer slotDurationMinutes;

    private LocalDate effectiveFrom;
    private LocalDate effectiveTo;

    private AvailabilityStatus status;
}

