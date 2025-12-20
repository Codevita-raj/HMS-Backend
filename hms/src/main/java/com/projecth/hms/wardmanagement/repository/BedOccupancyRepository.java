package com.projecth.hms.wardmanagement.repository;

import com.projecth.hms.wardmanagement.entity.BedOccupancy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BedOccupancyRepository extends JpaRepository<BedOccupancy, Long> {

    boolean existsByBedIdAndOccupiedTillIsNull(Long bedId);

    Optional<BedOccupancy> findByBedIdAndOccupiedTillIsNull(Long bedId);
    Optional<BedOccupancy> findByAdmissionIdAndOccupiedTillIsNull(Long admissionId);

    Optional<BedOccupancy> findTopByAdmissionIdOrderByOccupiedFromDesc(Long admissionId);
}

