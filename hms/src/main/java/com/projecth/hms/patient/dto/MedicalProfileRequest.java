package com.projecth.hms.patient.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MedicalProfileRequest {
    private Long patientId;
    private String bloodGroup;
    private Double height;
    private Double weight;
    private String allergies;
    private String chronicConditions;
    private String notes;
}

