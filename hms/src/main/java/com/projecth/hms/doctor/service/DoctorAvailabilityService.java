package com.projecth.hms.doctor.service;

import java.time.LocalDateTime;
import java.util.List;

import com.projecth.hms.doctor.entity.DoctorAvailability;
import com.projecth.hms.doctor.repository.DoctorAvailabilityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.projecth.hms.shared.enums.AvailabilityStatus;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityService {

    private final DoctorAvailabilityRepository availabilityRepository;

    public DoctorAvailability createAvailability(
            DoctorAvailability availability
    ) {

        availability.setStatus(AvailabilityStatus.ACTIVE);
        availability.setCreatedAt(LocalDateTime.now());
        return availabilityRepository.save(availability);
    }

    public DoctorAvailability updateAvailability(
            Long availabilityId,
            DoctorAvailability updated
    ) {
        DoctorAvailability existing =
                availabilityRepository.findById(availabilityId)
                        .orElseThrow(() ->
                                new RuntimeException("Availability not found"));

        existing.setDayOfWeek(updated.getDayOfWeek());
        existing.setStartTime(updated.getStartTime());
        existing.setEndTime(updated.getEndTime());
        existing.setSlotDurationMinutes(updated.getSlotDurationMinutes());
        existing.setEffectiveFrom(updated.getEffectiveFrom());
        existing.setEffectiveTo(updated.getEffectiveTo());
        existing.setStatus(updated.getStatus());
        existing.setUpdatedAt(LocalDateTime.now());

        return availabilityRepository.save(existing);
    }

    public List<DoctorAvailability> getDoctorAvailability(Long doctorId) {
        return availabilityRepository.findByDoctorIdAndStatus(
                doctorId, AvailabilityStatus.ACTIVE
        );
    }
    @Transactional
    public void deactivateAvailability(Long availabilityId) {
        DoctorAvailability availability =
                availabilityRepository.findById(availabilityId)
                        .orElseThrow(() ->
                                new RuntimeException("Availability not found"));

        availability.setStatus(AvailabilityStatus.INACTIVE);
        availabilityRepository.save(availability);
    }

}

