package com.projecth.hms.patient.service;

import com.projecth.hms.patient.dto.MedicalProfileRequest;
import com.projecth.hms.patient.dto.MedicalProfileResponse;
import com.projecth.hms.patient.entity.Patient;
import com.projecth.hms.patient.entity.PatientAdmission;
import com.projecth.hms.patient.entity.PatientMedicalProfile;
import com.projecth.hms.patient.repository.PatientAdmissionRepository;
import com.projecth.hms.patient.repository.PatientMedicalProfileRepository;
import com.projecth.hms.patient.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PatientMedicalProfileService {
    private final PatientMedicalProfileRepository patientMedicalProfileRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public MedicalProfileResponse createPatientProfile(MedicalProfileRequest medicalProfileRequest){
        Patient  patient = patientRepository.findById(medicalProfileRequest.getPatientId())
                .orElseThrow(()-> new RuntimeException("Patient not found"));

        if (patientMedicalProfileRepository.findByPatientId(medicalProfileRequest.getPatientId()).isPresent()) {
            throw new RuntimeException("Medical profile already exists");
        }

        PatientMedicalProfile profile = new PatientMedicalProfile();

        profile.setPatientId(medicalProfileRequest.getPatientId());
        profile.setBloodGroup(medicalProfileRequest.getBloodGroup());
        profile.setHeight(medicalProfileRequest.getHeight());
        profile.setWeight(medicalProfileRequest.getWeight());
        profile.setAllergies(medicalProfileRequest.getAllergies());
        profile.setChronicConditions(medicalProfileRequest.getChronicConditions());
        profile.setNotes(medicalProfileRequest.getNotes());
        profile.setCreatedAt(LocalDateTime.now());
        profile.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(patientMedicalProfileRepository.save(profile));

    }
    @Transactional
    public MedicalProfileResponse updatePatientProfile(Long medicalProfileId,MedicalProfileRequest medicalProfileRequest){
        Patient  patient = patientRepository.findById(medicalProfileRequest.getPatientId())
                .orElseThrow(()-> new RuntimeException("Patient not found"));



        PatientMedicalProfile profile =
                patientMedicalProfileRepository.findById(medicalProfileId)
                        .orElseThrow(() -> new RuntimeException("Medical profile not found"));


        profile.setPatientId(medicalProfileRequest.getPatientId());
        profile.setBloodGroup(medicalProfileRequest.getBloodGroup());
        profile.setHeight(medicalProfileRequest.getHeight());
        profile.setWeight(medicalProfileRequest.getWeight());
        profile.setAllergies(medicalProfileRequest.getAllergies());
        profile.setChronicConditions(medicalProfileRequest.getChronicConditions());
        profile.setNotes(medicalProfileRequest.getNotes());
        profile.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(patientMedicalProfileRepository.save(profile));

    }
    public MedicalProfileResponse getMedicalProfile(Long medicalProfileId){

        PatientMedicalProfile profile = patientMedicalProfileRepository.findById(medicalProfileId)
                .orElseThrow(()-> new RuntimeException("Patient Medical profile not found"));
        return mapToResponse(profile);
    }
    public MedicalProfileResponse getByPatient(Long patientId) {
        PatientMedicalProfile profile = patientMedicalProfileRepository.findByPatientId(patientId)
                .orElseThrow(() -> new RuntimeException("Medical profile not found"));

        return mapToResponse(profile);
    }

    private MedicalProfileResponse mapToResponse(PatientMedicalProfile profile) {
        return MedicalProfileResponse.builder()
                .patientMedicalProfileId(profile.getId())
                .patientId(profile.getPatientId())
                .bloodGroup(profile.getBloodGroup())
                .height(profile.getHeight())
                .weight(profile.getWeight())
                .allergies(profile.getAllergies())
                .chronicConditions(profile.getChronicConditions())
                .notes(profile.getNotes())
                .build();
    }
}

