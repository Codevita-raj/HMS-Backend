package com.projecth.hms.lab.dto.labOrder;

import com.projecth.hms.shared.enums.LabOrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class LabOrderResponse {

    private Long labOrderId;
    private Long patientId;
    private Long admissionId;
    private Long doctorId;

    private LabOrderStatus status;
    private LocalDateTime orderedAt;

    private List<LabOrderItemResponse> items;
}

