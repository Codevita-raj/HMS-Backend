package com.projecth.hms.patient.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionDischargeRequest {
    private LocalDateTime dischargeDate;
    private String remarks;
}
