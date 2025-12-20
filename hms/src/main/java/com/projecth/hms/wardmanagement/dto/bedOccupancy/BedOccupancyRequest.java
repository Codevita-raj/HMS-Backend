package com.projecth.hms.wardmanagement.dto.bedOccupancy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BedOccupancyRequest {

    private Long bedId;
    private Long patientId;
    private Long admissionId;
    private String remarks;
}

