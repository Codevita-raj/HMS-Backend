package com.projecth.hms.lab.dto.labOrder;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LabOrderRequest {

    private Long patientId;
    private Long admissionId;
    private Long doctorId;

    private List<Long> labTestIds;
}

