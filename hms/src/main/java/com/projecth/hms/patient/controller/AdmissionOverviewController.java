package com.projecth.hms.patient.controller;

import com.projecth.hms.patient.dto.AdmissionOverviewResponse;
import com.projecth.hms.patient.service.AdmissionOverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AdmissionOverviewController {
    private final AdmissionOverviewService admissionOverviewService;
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','NURSE')")
    @GetMapping("/patients/admission/{admissionId}")
    public ResponseEntity<AdmissionOverviewResponse> getAdmissionOverview(@PathVariable Long admissionId){
        AdmissionOverviewResponse admissionOverviewResponse = admissionOverviewService.getAdmissionOverview(admissionId);
        return ResponseEntity.status(HttpStatus.OK).body(admissionOverviewResponse);
    }
}
