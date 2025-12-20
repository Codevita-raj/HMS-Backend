package com.projecth.hms.lab.dto.labTest;

import com.projecth.hms.shared.enums.LabTestType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class LabTestResponse {

    private Long labTestId;
    private String testCode;
    private String testName;
    private LabTestType testType;
    private BigDecimal price;
    private Boolean active;
    private Long departmentId;
}

