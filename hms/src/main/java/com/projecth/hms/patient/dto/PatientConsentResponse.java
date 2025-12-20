package com.projecth.hms.patient.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@Builder
public class PatientConsentResponse {
    private Long patientConsentId;
    private Long patientId;
    private String consentType;
    private Boolean consentGiven;
    private String consentGivenBy;
    private String guardianName;
    private LocalDateTime consentDate;
    private LocalDateTime createdAt;
    private String remarks;
}
