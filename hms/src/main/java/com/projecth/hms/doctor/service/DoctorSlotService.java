package com.projecth.hms.doctor.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.projecth.hms.doctor.dto.DoctorSlotResponse;
import com.projecth.hms.doctor.entity.DoctorAvailability;
import com.projecth.hms.doctor.entity.DoctorSlot;
import com.projecth.hms.doctor.repository.DoctorAvailabilityRepository;
import com.projecth.hms.doctor.repository.DoctorSlotRepository;
import com.projecth.hms.shared.enums.AvailabilityStatus;
import com.projecth.hms.shared.enums.SlotStatus;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorSlotService {

    private final DoctorSlotRepository doctorSlotRepository;


    private final DoctorAvailabilityRepository availabilityRepository;
    private final DoctorSlotRepository slotRepository;

    public void generateSlotsForNextDays(int daysAhead) {

        List<DoctorAvailability> availabilities =
                availabilityRepository.findByStatus(AvailabilityStatus.ACTIVE);

        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(daysAhead);

        for (DoctorAvailability availability : availabilities) {

            generateSlotsForAvailability(
                    availability,
                    today,
                    endDate
            );
        }
    }

    private void generateSlotsForAvailability(
            DoctorAvailability availability,
            LocalDate startDate,
            LocalDate endDate
    ) {

        for (LocalDate date = startDate;
             !date.isAfter(endDate);
             date = date.plusDays(1)) {

            // Match day of week
            if (date.getDayOfWeek() != availability.getDayOfWeek()) {
                continue;
            }

            // Effective date check
            if (!isWithinEffectivePeriod(availability, date)) {
                continue;
            }

            generateSlotsForDate(availability, date);
        }
    }

    private void generateSlotsForDate(
            DoctorAvailability availability,
            LocalDate date
    ) {

        LocalTime slotStart = availability.getStartTime();
        LocalTime endTime = availability.getEndTime();
        int duration = availability.getSlotDurationMinutes();

        while (slotStart.plusMinutes(duration).compareTo(endTime) <= 0) {

            // Prevent duplicates
            boolean exists = slotRepository.existsByDoctorIdAndSlotDateAndStartTime(
                    availability.getDoctorId(),
                    date,
                    slotStart
            );

            if (!exists) {
                DoctorSlot slot = new DoctorSlot();
                slot.setDoctorId(availability.getDoctorId());
                slot.setSlotDate(date);
                slot.setStartTime(slotStart);
                slot.setEndTime(slotStart.plusMinutes(duration));
                slot.setStatus(SlotStatus.AVAILABLE);
                slot.setAvailabilityId(availability.getId());
                slot.setCreatedAt(LocalDateTime.now());

                slotRepository.save(slot);
            }

            slotStart = slotStart.plusMinutes(duration);
        }
    }

    private boolean isWithinEffectivePeriod(
            DoctorAvailability availability,
            LocalDate date
    ) {
        if (availability.getEffectiveFrom() != null
                && date.isBefore(availability.getEffectiveFrom())) {
            return false;
        }

        if (availability.getEffectiveTo() != null
                && date.isAfter(availability.getEffectiveTo())) {
            return false;
        }

        return true;
    }
}


