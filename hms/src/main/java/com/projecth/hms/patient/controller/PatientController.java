package com.projecth.hms.patient.controller;

import com.projecth.hms.patient.dto.PatientRequest;
import com.projecth.hms.patient.dto.PatientResponse;
import com.projecth.hms.patient.entity.Patient;
import com.projecth.hms.patient.repository.PatientRepository;
import com.projecth.hms.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("patient")
    public ResponseEntity<PatientResponse> addPatient(@Valid @RequestBody PatientRequest patientRequest){
        PatientResponse patient = patientService.addPatient(patientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }
    @GetMapping("patient/{patientId}")
    public ResponseEntity<PatientResponse> getPatientDetails(@PathVariable Long patientId){
        PatientResponse patient = patientService.getPatientDetails(patientId);
        return ResponseEntity.status(HttpStatus.OK).body(patient);
    }
    @PutMapping("patient/{patientId}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long patientId, @Valid @RequestBody PatientRequest patientRequest){
        PatientResponse patient = patientService.updatePatient(patientId,patientRequest);
        return ResponseEntity.status(HttpStatus.OK).body(patient);
    }
    @DeleteMapping("patient/{patientId}")
    public ResponseEntity<?> deletePatient(@PathVariable Long patientId){
        patientService.deletePatient(patientId);
        return ResponseEntity.status(HttpStatus.OK).body("Patient deleted successfully");
    }
}
