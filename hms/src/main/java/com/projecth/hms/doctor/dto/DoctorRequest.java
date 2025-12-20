package com.projecth.hms.doctor.dto;

import com.projecth.hms.address.dto.AddressRequest;
import com.projecth.hms.shared.enums.Specialization;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRequest {

    private String firstName;
    private String lastName;
    private Specialization specialization;
    private String phone;
    private String email;
    private Integer experience;
    private AddressRequest address;
    private Long departmentId;

}

