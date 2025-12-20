package com.projecth.hms.staff.dto;

import com.projecth.hms.shared.enums.StaffType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffRequest {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private StaffType staffType;
    private Long departmentId;
    private Long addressId;
}

