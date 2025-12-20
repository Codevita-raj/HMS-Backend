package com.projecth.hms.patient.service;

import com.projecth.hms.patient.dto.AdmissionOverviewResponse;
import com.projecth.hms.patient.dto.AdmissionResponse;
import com.projecth.hms.patient.dto.AssignmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdmissionOverviewService {
private final PatientAdmissionService patientAdmissionService;
private final PatientCareAssignmentService patientCareAssignmentService;

public AdmissionOverviewResponse getAdmissionOverview(Long admissionId){

    AdmissionResponse admissionResponse = patientAdmissionService.getAdmissionById(admissionId);
    List<AssignmentResponse> assignmentResponse = patientCareAssignmentService.getAssignmentsByAdmission(admissionId);

    return AdmissionOverviewResponse.builder()
            .admission(admissionResponse)
            .activeAssignments(assignmentResponse)
            .build();
}
}
