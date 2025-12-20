package com.projecth.hms.wardmanagement.dto.ward;

import com.projecth.hms.shared.enums.WardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WardRequest {
    private String name;
    private WardType type;
    private Long departmentId;
}
