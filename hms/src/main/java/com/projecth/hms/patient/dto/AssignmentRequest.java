package com.projecth.hms.patient.dto;

import com.projecth.hms.shared.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentRequest {
    private Long admissionId;
    private Long staffId;
    private Role role;
}
