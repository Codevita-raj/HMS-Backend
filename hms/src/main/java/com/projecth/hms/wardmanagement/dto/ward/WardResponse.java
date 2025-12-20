package com.projecth.hms.wardmanagement.dto.ward;

import com.projecth.hms.shared.enums.WardStatus;
import com.projecth.hms.shared.enums.WardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WardResponse {
    private Long id;
    private String name;
    private WardType type;
    private WardStatus status;
    private Long departmentId;
}

