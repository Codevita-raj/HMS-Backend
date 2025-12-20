package com.projecth.hms.patient.controller;

import com.projecth.hms.patient.dto.PatientConsentRequest;
import com.projecth.hms.patient.dto.PatientConsentResponse;
import com.projecth.hms.patient.service.PatientConsentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PatientConsentController {

    private final PatientConsentService patientConsentService;

    @PostMapping("/consents")
    public ResponseEntity<PatientConsentResponse> createConsent(
            @RequestBody PatientConsentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientConsentService.createPatientConsent(request));
    }

    @GetMapping("/consents/{consentId}")
    public ResponseEntity<PatientConsentResponse> getConsentById(
            @PathVariable Long consentId) {
        return ResponseEntity.ok(patientConsentService.getConsentById(consentId));
    }

    @GetMapping("/consents/latest/patients/{patientId}")
    public ResponseEntity<PatientConsentResponse> getLatestConsent(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(patientConsentService.getLatestConsentByPatient(patientId));
    }

    @GetMapping("/consents/patients/{patientId}")
    public ResponseEntity<List<PatientConsentResponse>> getConsentHistory(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(patientConsentService.getConsentHistory(patientId));
    }
}
