package com.projecth.hms.patient.dto;

import com.projecth.hms.shared.enums.AdmissionStatus;
import com.projecth.hms.shared.enums.AdmissionType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionCreateRequest {
    private Long patientId;
    private AdmissionType admissionType;
    private LocalDateTime admissionDate;
    private String reasonForAdmission;
    private String remarks;
    private Long bedId;
}
