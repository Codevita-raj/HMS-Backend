package com.projecth.hms.patient.service;

import com.projecth.hms.patient.dto.*;
import com.projecth.hms.patient.entity.*;
import com.projecth.hms.patient.repository.*;
import com.projecth.hms.shared.enums.AdmissionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientProfileService {

    private final PatientRepository patientRepository;
    private final PatientMedicalProfileRepository medicalProfileRepository;
    private final PatientConsentRepository patientConsentRepository;
    private final PatientEmergencyContactRepository emergencyContactRepository;
    private final PatientAdmissionRepository patientAdmissionRepository;

    public PatientProfileResponse getPatientProfile(Long patientId) {

        //  Patient (mandatory)
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        PatientResponse patientResponse = PatientResponse.builder()
                .patientId(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .gender(patient.getGender())
                .dob(patient.getDob())
                .phone(patient.getPhone())
                .email(patient.getEmail())
                .build();

        //  Medical Profile (optional)
        MedicalProfileResponse medicalProfile =
                medicalProfileRepository.findByPatientId(patientId)
                        .map(profile -> MedicalProfileResponse.builder()
                                .patientMedicalProfileId(profile.getId())
                                .patientId(profile.getPatientId())
                                .bloodGroup(profile.getBloodGroup())
                                .height(profile.getHeight())
                                .weight(profile.getWeight())
                                .allergies(profile.getAllergies())
                                .chronicConditions(profile.getChronicConditions())
                                .notes(profile.getNotes())
                                .build())
                        .orElse(null);

        //  Consent (derived boolean)
        Optional<PatientConsent> consentOpt =
                patientConsentRepository
                        .findFirstByPatientIdOrderByConsentDateDesc(patientId);

        Boolean consentGiven = consentOpt
                .map(PatientConsent::getConsentGiven)
                .orElse(false);


        //  Primary Emergency Contact (optional)
        EmergencyContactResponse primaryEmergencyContact =
                emergencyContactRepository.findByPatientIdAndPrimaryContactTrue(patientId)
                        .map(contact -> EmergencyContactResponse.builder()
                                .contactId(contact.getId())
                                .name(contact.getName())
                                .relation(contact.getRelation())
                                .phone(contact.getPhone())
                                .primaryContact(contact.getPrimaryContact())
                                .build())
                        .orElse(null);

        //  Active Admission (derived)
        Optional<PatientAdmission> activeAdmission =
                patientAdmissionRepository
                        .findFirstByPatientIdAndStatusIn(
                                patientId,
                                List.of(
                                        AdmissionStatus.ADMITTED,
                                        AdmissionStatus.IN_TREATMENT
                                )
                        );

        Boolean currentlyAdmitted = activeAdmission.isPresent();
        Long activeAdmissionId =
                activeAdmission.map(PatientAdmission::getAdmissionId).orElse(null);

        // ✅ Final Aggregated Response
        return PatientProfileResponse.builder()
                .patient(patientResponse)
                .medicalProfile(medicalProfile)
                .consentGiven(consentGiven)
                .primaryEmergencyContact(primaryEmergencyContact)
                .currentlyAdmitted(currentlyAdmitted)
                .activeAdmissionId(activeAdmissionId)
                .build();
    }
}
