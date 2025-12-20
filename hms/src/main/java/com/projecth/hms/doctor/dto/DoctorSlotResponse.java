package com.projecth.hms.doctor.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.projecth.hms.shared.enums.SlotStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DoctorSlotResponse {

    private Long id;
    private Long doctorId;

    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private SlotStatus status;
}

