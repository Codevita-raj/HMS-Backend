package com.projecth.hms.wardmanagement.dto.bed;

import com.projecth.hms.shared.enums.BedStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BedResponse {
    private Long id;
    private String bedNumber;
    private BedStatus status;
    private Long wardId;
}

