package com.projecth.hms.patient.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmergencyContactResponse {
    private Long emergencyContactId;
    private Long contactId;
    private Long patientId;
    private String name;
    private String relation;
    private String phone;
    private String alternatePhone;
    private Boolean primaryContact;
}

