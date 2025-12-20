package com.projecth.hms.patient.dto;

import com.projecth.hms.address.dto.AddressRequest;
import com.projecth.hms.shared.enums.Gender;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientRequest {
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dob;
    private String email;
    private String phone;
    private Long addressId;
    private Long assignedDoctorId;
    private AddressRequest address;
}

