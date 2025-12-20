package com.projecth.hms.wardmanagement.repository;

import com.projecth.hms.wardmanagement.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WardRepository extends JpaRepository<Ward, Long> {}