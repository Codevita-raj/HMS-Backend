package com.projecth.hms.patient.dto;

import com.projecth.hms.shared.enums.AdmissionStatus;
import com.projecth.hms.shared.enums.AdmissionType;
import com.projecth.hms.shared.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentResponse {
    private Long assignmentId;
    private Long admissionId;
    private Long staffId;
    private Role role;
    private LocalDateTime assignedFrom;
    private LocalDateTime assignedTo;;
    private Boolean active;
}
