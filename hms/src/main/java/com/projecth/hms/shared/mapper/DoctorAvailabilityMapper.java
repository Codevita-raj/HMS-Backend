package com.projecth.hms.shared.mapper;

import com.projecth.hms.doctor.dto.DoctorAvailabilityRequest;
import com.projecth.hms.doctor.dto.DoctorAvailabilityResponse;
import com.projecth.hms.doctor.entity.DoctorAvailability;

public class DoctorAvailabilityMapper {

    public static DoctorAvailability toEntity(DoctorAvailabilityRequest req) {
        DoctorAvailability a = new DoctorAvailability();
        a.setDoctorId(req.getDoctorId());
        a.setDayOfWeek(req.getDayOfWeek());
        a.setStartTime(req.getStartTime());
        a.setEndTime(req.getEndTime());
        a.setSlotDurationMinutes(req.getSlotDurationMinutes());
        a.setEffectiveFrom(req.getEffectiveFrom());
        a.setEffectiveTo(req.getEffectiveTo());
        return a;
    }

    public static DoctorAvailabilityResponse toResponse(DoctorAvailability a) {
        return DoctorAvailabilityResponse.builder()
                .availabilityId(a.getId())
                .doctorId(a.getDoctorId())
                .dayOfWeek(a.getDayOfWeek())
                .startTime(a.getStartTime())
                .endTime(a.getEndTime())
                .slotDurationMinutes(a.getSlotDurationMinutes())
                .effectiveFrom(a.getEffectiveFrom())
                .effectiveTo(a.getEffectiveTo())
                .status(a.getStatus())
                .build();
    }
}

