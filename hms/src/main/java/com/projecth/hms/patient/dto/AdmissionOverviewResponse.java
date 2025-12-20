package com.projecth.hms.patient.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AdmissionOverviewResponse {

    private AdmissionResponse admission;
    private List<AssignmentResponse> activeAssignments;
}

