package com.projecth.hms.patient.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PatientProfileResponse {

    // Core
    private PatientResponse patient;

    // Medical
    private MedicalProfileResponse medicalProfile;

    // Consent
    private Boolean consentGiven;

    // Emergency
    private EmergencyContactResponse primaryEmergencyContact;

    // Admission (derived)
    private Boolean currentlyAdmitted;
    private Long activeAdmissionId;
}

