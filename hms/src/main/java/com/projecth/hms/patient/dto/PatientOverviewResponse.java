package com.projecth.hms.patient.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PatientOverviewResponse {

    private PatientResponse patient;
    private MedicalProfileResponse medicalProfile;
    private PatientConsentResponse consent;
    private List<EmergencyContactResponse> emergencyContacts;
}

