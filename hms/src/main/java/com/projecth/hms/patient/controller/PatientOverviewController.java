package com.projecth.hms.patient.controller;

import com.projecth.hms.patient.dto.PatientOverviewResponse;
import com.projecth.hms.patient.service.PatientOverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PatientOverviewController {

    private final PatientOverviewService patientOverviewService;
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','NURSE')")
    @GetMapping("/patients/overview/{patientId}")
    public ResponseEntity<PatientOverviewResponse> getPatientAggregate(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                patientOverviewService.getPatientOverview(patientId));
    }
}
