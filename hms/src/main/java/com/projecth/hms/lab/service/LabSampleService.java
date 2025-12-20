package com.projecth.hms.lab.service;

import com.projecth.hms.lab.dto.labOrder.LabResultRequest;
import com.projecth.hms.lab.dto.labOrder.SampleCollectRequest;
import com.projecth.hms.lab.entity.LabOrderItem;
import com.projecth.hms.lab.repository.LabOrderItemRepository;
import com.projecth.hms.shared.enums.LabSampleStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LabSampleService {

    private final LabOrderItemRepository labOrderItemRepository;

    @Transactional
    public void collectSample(Long labOrderItemId, SampleCollectRequest request) {

        LabOrderItem item = labOrderItemRepository.findById(labOrderItemId)
                .orElseThrow(() -> new RuntimeException("Lab order item not found"));

        if (item.getSampleStatus() != LabSampleStatus.NOT_COLLECTED) {
            throw new RuntimeException("Sample already collected or processed");
        }

        item.setSampleStatus(LabSampleStatus.COLLECTED);
        item.setSampleCollectedAt(LocalDateTime.now());
        item.setResultRemarks(request.getRemarks());

        labOrderItemRepository.save(item);
    }

    @Transactional
    public void enterResult(Long labOrderItemId, LabResultRequest request) {

        LabOrderItem item = labOrderItemRepository.findById(labOrderItemId)
                .orElseThrow(() -> new RuntimeException("Lab order item not found"));

        if (item.getSampleStatus() != LabSampleStatus.COLLECTED) {
            throw new RuntimeException("Sample not yet collected");
        }

        item.setSampleStatus(LabSampleStatus.REPORTED);
        item.setResultValue(request.getResultValue());
        item.setResultRemarks(request.getResultRemarks());
        item.setResultUpdatedAt(LocalDateTime.now());

        labOrderItemRepository.save(item);
    }
}

