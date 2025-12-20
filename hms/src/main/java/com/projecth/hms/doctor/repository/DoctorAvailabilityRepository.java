package com.projecth.hms.doctor.repository;

import java.util.List;
import com.projecth.hms.doctor.entity.DoctorAvailability;
import com.projecth.hms.shared.enums.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorAvailabilityRepository
        extends JpaRepository<DoctorAvailability, Long> {

    List<DoctorAvailability> findByStatus(AvailabilityStatus status);

    List<DoctorAvailability> findByDoctorIdAndStatus(
            Long doctorId,
            AvailabilityStatus status
    );
}

