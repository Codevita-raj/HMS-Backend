package com.projecth.hms.patient.controller;

import com.projecth.hms.patient.dto.PatientConsentRequest;
import com.projecth.hms.patient.dto.PatientConsentResponse;
import com.projecth.hms.patient.service.PatientConsentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PatientConsentController {

    private final PatientConsentService patientConsentService;
    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/consents")
    public ResponseEntity<PatientConsentResponse> createConsent(
            @RequestBody PatientConsentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientConsentService.createPatientConsent(request));
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','PATIENT')")
    @GetMapping("/consents/{consentId}")
    public ResponseEntity<PatientConsentResponse> getConsentById(
            @PathVariable Long consentId) {
        return ResponseEntity.ok(patientConsentService.getConsentById(consentId));
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR',PATIENT')")
    @GetMapping("/consents/latest/patients/{patientId}")
    public ResponseEntity<PatientConsentResponse> getLatestConsent(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(patientConsentService.getLatestConsentByPatient(patientId));
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/consents/patients/{patientId}")
    public ResponseEntity<List<PatientConsentResponse>> getConsentHistory(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(patientConsentService.getConsentHistory(patientId));
    }
}
