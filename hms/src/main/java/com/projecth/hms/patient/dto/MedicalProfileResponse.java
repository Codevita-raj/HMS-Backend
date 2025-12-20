package com.projecth.hms.patient.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MedicalProfileResponse {
    private Long patientMedicalProfileId;
    private Long patientId;
    private String bloodGroup;
    private Double height;
    private Double weight;
    private String allergies;
    private String chronicConditions;
    private String notes;
}

