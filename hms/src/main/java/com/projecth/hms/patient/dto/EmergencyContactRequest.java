package com.projecth.hms.patient.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmergencyContactRequest {

    private Long patientId;
    private String name;
    private String relation;
    private String phone;
    private String alternatePhone;
    private Boolean primaryContact;
}

