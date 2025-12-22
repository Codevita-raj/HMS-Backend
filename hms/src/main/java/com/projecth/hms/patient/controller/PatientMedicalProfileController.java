package com.projecth.hms.patient.controller;

import com.projecth.hms.patient.dto.MedicalProfileRequest;
import com.projecth.hms.patient.dto.MedicalProfileResponse;
import com.projecth.hms.patient.entity.PatientMedicalProfile;
import com.projecth.hms.patient.service.PatientMedicalProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PatientMedicalProfileController {

    private final PatientMedicalProfileService patientMedicalProfileService;
    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PostMapping("/medical/profile")
    public ResponseEntity<MedicalProfileResponse> createMedicalProfile(
            @RequestBody MedicalProfileRequest request) {

        MedicalProfileResponse medicalProfileResponse =
                patientMedicalProfileService.createPatientProfile(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalProfileResponse);
    }
    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PutMapping("/medical/profile/{medicalProfileId}")
    public ResponseEntity<MedicalProfileResponse> updateMedicalProfile(@PathVariable Long medicalProfileId,
            @RequestBody MedicalProfileRequest request) {

        MedicalProfileResponse medicalProfileResponse =
                patientMedicalProfileService.updatePatientProfile(medicalProfileId,request);
        return ResponseEntity.status(HttpStatus.OK).body(medicalProfileResponse);
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','NURSE')")
    @GetMapping("/medical/profile/patient/{patientId}")
    public ResponseEntity<MedicalProfileResponse> getByPatient(@PathVariable Long patientId) {
        MedicalProfileResponse medicalProfileResponse =
                patientMedicalProfileService.getByPatient(patientId);
        return ResponseEntity.status(HttpStatus.OK).body(medicalProfileResponse);
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','NURSE')")
    @GetMapping("/medical/profile/{medicalProfileId}")
    public ResponseEntity<MedicalProfileResponse> getByMedicalProfile(@PathVariable Long medicalProfileId) {
        MedicalProfileResponse medicalProfileResponse =
                patientMedicalProfileService.getMedicalProfile(medicalProfileId);
        return ResponseEntity.status(HttpStatus.OK).body(medicalProfileResponse);
    }
}