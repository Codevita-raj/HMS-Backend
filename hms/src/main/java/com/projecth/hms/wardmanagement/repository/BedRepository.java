package com.projecth.hms.wardmanagement.repository;

import com.projecth.hms.wardmanagement.entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BedRepository extends JpaRepository<Bed, Long> {
    boolean existsByWardIdAndBedNumber(Long wardId, String bedNumber);
}
