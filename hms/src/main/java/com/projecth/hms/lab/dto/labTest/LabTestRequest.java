package com.projecth.hms.lab.dto.labTest;

import com.projecth.hms.shared.enums.LabTestType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LabTestRequest {

    private String testCode;
    private String testName;
    private LabTestType testType;
    private BigDecimal price;
    private Long departmentId;
}

