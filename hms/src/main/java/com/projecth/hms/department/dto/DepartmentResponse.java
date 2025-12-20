package com.projecth.hms.department.dto;

import com.projecth.hms.shared.enums.DepartmentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentResponse {
    private Long id;
    private String name;
    private String code;
    private DepartmentStatus status;
}
