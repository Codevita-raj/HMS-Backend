package com.projecth.hms.patient.dto;

import com.projecth.hms.address.dto.AddressResponse;
import com.projecth.hms.address.entity.Address;
import com.projecth.hms.shared.enums.Gender;
import com.projecth.hms.shared.enums.PatientStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientResponse {
    private Long patientId;
    private String patientCode;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate dob;
    private String email;
    private String phone;
    private PatientStatus status;
    private AddressResponse address;
    private Long assignedDoctorId;
}

