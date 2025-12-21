package com.projecth.hms.lab.repository;

import com.projecth.hms.lab.entity.LabOrderItem;
import com.projecth.hms.shared.enums.LabSampleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabOrderItemRepository extends JpaRepository<LabOrderItem, Long> {
    List<LabOrderItem> findByLabOrderId(Long labOrderId);
    boolean existsByLabOrderIdAndSampleStatusNot(
            Long labOrderId, LabSampleStatus status);

    boolean existsByLabOrderIdInAndSampleStatusIn(
            List<Long> labOrderIds,
            List<LabSampleStatus> statuses
    );


    List<LabOrderItem> findByLabOrderIdIn(List<Long> labOrderIds);
}

