package com.projecth.hms.doctor.dto;

import com.projecth.hms.address.dto.AddressResponse;
import com.projecth.hms.shared.enums.DoctorStatus;
import com.projecth.hms.shared.enums.Specialization;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DoctorResponse {

    private Long doctorId;
    private String firstName;
    private String lastName;
    private Specialization specialization;
    private String phone;
    private String email;
    private Integer experience;
    private DoctorStatus status;
    private AddressResponse address;
    private Long departmentId;

}

