package com.projecth.hms.inventory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientMedicineIssueRequest {

    private Long patientId;
    private Long admissionId;

    private Long itemId;
    private Long locationId;

    private Integer quantity;

    private String remarks;
}

