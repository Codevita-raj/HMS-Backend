package com.projecth.hms.patient.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientConsentRequest {
    private Long patientId;
    private String consentType;
    private Boolean consentGiven;
    private String consentGivenBy;
    private String guardianName;
    private String remarks;
}
