package com.projecth.hms.lab.repository;

import com.projecth.hms.lab.entity.LabTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LabTestRepository extends JpaRepository<LabTest, Long> {

    Optional<LabTest> findByTestCode(String testCode);

    boolean existsByTestCode(String testCode);
}

