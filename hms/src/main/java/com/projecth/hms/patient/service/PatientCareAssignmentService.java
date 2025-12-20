package com.projecth.hms.patient.service;

import com.projecth.hms.patient.dto.AssignmentRequest;
import com.projecth.hms.patient.dto.AssignmentResponse;
import com.projecth.hms.patient.entity.PatientAdmission;
import com.projecth.hms.patient.entity.PatientCareAssignment;
import com.projecth.hms.patient.repository.PatientAdmissionRepository;
import com.projecth.hms.patient.repository.PatientCareAssignmentRepository;
import com.projecth.hms.shared.enums.AdmissionStatus;
import com.projecth.hms.shared.enums.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientCareAssignmentService {
    private final PatientCareAssignmentRepository patientCareAssignmentRepository;
    private final PatientAdmissionRepository patientAdmissionRepository;

    @Transactional
    public AssignmentResponse assignStaff(AssignmentRequest assignmentRequest){

        PatientAdmission patientAdmission = patientAdmissionRepository.findById(assignmentRequest.getAdmissionId())
                .orElseThrow(()-> new RuntimeException("Admission not found with admissionId "));

        if (patientAdmission.getStatus() == AdmissionStatus.DISCHARGED ||
        patientAdmission.getStatus() == AdmissionStatus.CANCELLED){
            throw new RuntimeException("Cannot Assign staff to a discharged or cancelled admission");
        }

        if (assignmentRequest.getRole() == Role.DOCTOR){
             patientCareAssignmentRepository
                    .findByAdmissionIdAndRoleAndActiveTrue(assignmentRequest.getAdmissionId(), assignmentRequest.getRole())
                    .ifPresent(existing->{
                        existing.setAssignedTo(LocalDateTime.now());
                        existing.setActive(false);
                        patientCareAssignmentRepository.save(existing);
                    });
        }
        PatientCareAssignment patientCareAssignment = new PatientCareAssignment();
        patientCareAssignment.setStaffId(assignmentRequest.getStaffId());
        patientCareAssignment.setRole(assignmentRequest.getRole());
        patientCareAssignment.setAssignedFrom(LocalDateTime.now());
        patientCareAssignment.setAdmissionId(assignmentRequest.getAdmissionId());
        patientCareAssignment.setAssignedTo(null);
        patientCareAssignment.setActive(true);

return toMapAssignmentResponse(patientCareAssignmentRepository.save(patientCareAssignment));
    }

    @Transactional
    public AssignmentResponse endAssignment(Long assignmentId){
        PatientCareAssignment patientCareAssignment = patientCareAssignmentRepository.findById(assignmentId)
                .orElseThrow(()-> new RuntimeException("Assignment Not found with the AssignmentId "+assignmentId));
        if (!patientCareAssignment.getActive()) {
            throw new RuntimeException("Assignment already ended");
        }
        patientCareAssignment.setAssignedTo(LocalDateTime.now());
        patientCareAssignment.setActive(false);

        return toMapAssignmentResponse(patientCareAssignmentRepository.save(patientCareAssignment));
    }

    public List<AssignmentResponse> getAssignmentsByAdmission(Long admissionId){
        List<PatientCareAssignment> patientAdmission = patientCareAssignmentRepository.findByAdmissionId(admissionId);

        if (patientAdmission.isEmpty()){
          throw new RuntimeException("Admission Not found with the admissionId "+admissionId);
        }
        return patientAdmission.stream()
                .map(this::toMapAssignmentResponse)
                .toList();
    }


    //Helper Method
    private AssignmentResponse toMapAssignmentResponse(PatientCareAssignment patientCareAssignment){
        return AssignmentResponse.builder()
                .assignmentId(patientCareAssignment.getId())
                .admissionId(patientCareAssignment.getAdmissionId())
                .assignedTo(patientCareAssignment.getAssignedTo())
                .assignedFrom(patientCareAssignment.getAssignedFrom())
                .role(patientCareAssignment.getRole())
                .staffId(patientCareAssignment.getStaffId())
                .active(patientCareAssignment.getActive())
                .build();
    }

}
