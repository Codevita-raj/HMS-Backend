package com.projecth.hms.patient.controller;

import com.projecth.hms.patient.dto.PatientProfileResponse;
import com.projecth.hms.patient.service.PatientProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PatientProfileController {

    private final PatientProfileService patientProfileService;

    @GetMapping("/patients/profile/{patientId}")
    public ResponseEntity<PatientProfileResponse> getPatientProfile(
            @PathVariable Long patientId
    ) {
        return ResponseEntity.ok(
                patientProfileService.getPatientProfile(patientId)
        );
    }
}
