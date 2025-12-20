package com.projecth.hms.patient.service;

import com.projecth.hms.patient.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientOverviewService {
    private final PatientService patientService;
    private final PatientMedicalProfileService medicalProfileService;
    private final PatientConsentService patientConsentService;
    private final PatientEmergencyContactService emergencyContactService;

    public PatientOverviewResponse getPatientOverview(Long patientId){

        PatientResponse patientResponse = patientService.getPatientDetails(patientId);
        MedicalProfileResponse medicalProfileResponse = medicalProfileService.getByPatient(patientId);
        PatientConsentResponse patientConsentResponse = patientConsentService.getLatestConsentByPatient(patientId);
       List<EmergencyContactResponse> emergencyContactResponse = emergencyContactService.getContactsByPatient(patientId);

       return PatientOverviewResponse.builder()
               .patient(patientResponse)
               .medicalProfile(medicalProfileResponse)
               .consent(patientConsentResponse)
               .emergencyContacts(emergencyContactResponse)
               .build();
    }

}
