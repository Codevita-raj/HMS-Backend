package com.projecth.hms.doctor.repository;

import java.time.LocalDate;
import java.util.List;

import com.projecth.hms.doctor.entity.DoctorSlot;
import com.projecth.hms.shared.enums.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DoctorSlotRepository
        extends JpaRepository<DoctorSlot, Long> {

    boolean existsByDoctorIdAndSlotDateAndStartTime(
            Long doctorId,
            LocalDate slotDate,
            java.time.LocalTime startTime
    );

    List<DoctorSlot> findByDoctorIdAndSlotDateAndStatus(
            Long doctorId,
            LocalDate slotDate,
            SlotStatus status
    );
}

