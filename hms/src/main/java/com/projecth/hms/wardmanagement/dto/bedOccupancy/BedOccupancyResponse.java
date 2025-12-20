package com.projecth.hms.wardmanagement.dto.bedOccupancy;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BedOccupancyResponse {

    private Long occupancyId;
    private Long bedId;
    private Long patientId;
    private Long admissionId;
    private String remarks;

    private LocalDateTime occupiedFrom;
    private LocalDateTime occupiedTill;
}

