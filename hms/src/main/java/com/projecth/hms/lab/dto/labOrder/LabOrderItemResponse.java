package com.projecth.hms.lab.dto.labOrder;

import com.projecth.hms.shared.enums.LabSampleStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LabOrderItemResponse {

    private Long labTestId;
    private LabSampleStatus sampleStatus;
    private String resultValue;
}

