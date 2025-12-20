package com.projecth.hms.wardmanagement.dto.bed;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BedRequest {
    private String bedNumber;
    private Long wardId;
}


