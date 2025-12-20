package com.projecth.hms.patient.controller;

import com.projecth.hms.patient.dto.AssignmentRequest;
import com.projecth.hms.patient.dto.AssignmentResponse;
import com.projecth.hms.patient.service.PatientCareAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/assignments")
@RequiredArgsConstructor
public class PatientCareAssignmentController {
    private final PatientCareAssignmentService patientCareAssignmentService;

    @PostMapping
    public ResponseEntity<AssignmentResponse> assignStaff(
            @RequestBody AssignmentRequest assignmentRequest
    ) {
        AssignmentResponse response =
                patientCareAssignmentService.assignStaff(assignmentRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("/{assignmentId}/end")
    public ResponseEntity<AssignmentResponse> endAssignment(
            @PathVariable Long assignmentId
    ) {
        AssignmentResponse response =
                patientCareAssignmentService.endAssignment(assignmentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admission/{admissionId}")
    public ResponseEntity<List<AssignmentResponse>> getAssignmentsByAdmission(
            @PathVariable Long admissionId
    ) {
        List<AssignmentResponse> responses =
                patientCareAssignmentService.getAssignmentsByAdmission(admissionId);
        return ResponseEntity.ok(responses);
    }
}
