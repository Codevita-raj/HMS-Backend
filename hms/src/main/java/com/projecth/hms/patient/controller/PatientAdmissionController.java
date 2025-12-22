package com.projecth.hms.patient.controller;

import com.projecth.hms.patient.dto.AdmissionCreateRequest;
import com.projecth.hms.patient.dto.AdmissionDischargeRequest;
import com.projecth.hms.patient.dto.AdmissionResponse;
import com.projecth.hms.patient.dto.AdmissionUpdateRequest;
import com.projecth.hms.patient.service.PatientAdmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PatientAdmissionController {

    private final PatientAdmissionService patientAdmissionService;
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    @PostMapping("/admission")
    public ResponseEntity<AdmissionResponse> createAdmission(@RequestBody AdmissionCreateRequest admissionCreateRequest){
        AdmissionResponse patientAdmission = patientAdmissionService.createAdmission(admissionCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientAdmission);
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PutMapping("/admission/{admissionId}")
    public ResponseEntity<AdmissionResponse> updateAdmission(@PathVariable Long admissionId, @RequestBody AdmissionUpdateRequest admissionUpdateRequest){
        AdmissionResponse patientAdmission = patientAdmissionService.updateAdmission(admissionId,admissionUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(patientAdmission);
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PutMapping("/admission/{admissionId}/discharge")
    public ResponseEntity<AdmissionResponse> dischargeAdmission(@PathVariable Long admissionId, @RequestBody AdmissionDischargeRequest admissionDischargeRequest){
        AdmissionResponse patientAdmission = patientAdmissionService.dischargeAdmission(admissionId,admissionDischargeRequest);
        return ResponseEntity.status(HttpStatus.OK).body(patientAdmission);
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','NURSE','RECEPTIONIST')")
    @GetMapping("/admission/patient/{patientId}")
    public ResponseEntity<List<AdmissionResponse>> getAllAdmissions(@PathVariable Long patientId){
        List<AdmissionResponse> patientAdmission = patientAdmissionService.getAllAdmissions(patientId);
        return ResponseEntity.status(HttpStatus.OK).body(patientAdmission);
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','NURSE','RECEPTIONIST')")
    @GetMapping("/admission/{admissionId}")
    public ResponseEntity<?> getAdmissionById(@PathVariable Long admissionId){
        AdmissionResponse patientAdmission = patientAdmissionService.getAdmissionById(admissionId);
        return ResponseEntity.status(HttpStatus.OK).body(patientAdmission);
    }
}
