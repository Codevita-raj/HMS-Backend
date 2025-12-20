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
public class AdmissionResponse {
    private Long admissionId;
    private Long patientId;
    private Long bedId;
    private AdmissionType admissionType;
    private AdmissionStatus status;
    private LocalDateTime admissionDate;
    private LocalDateTime dischargeDate;
    private String reasonForAdmission;
    private String remarks;
}
