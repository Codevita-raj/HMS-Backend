package com.projecth.hms.patient.controller;

import com.projecth.hms.patient.dto.EmergencyContactRequest;
import com.projecth.hms.patient.dto.EmergencyContactResponse;
import com.projecth.hms.patient.service.PatientEmergencyContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PatientEmergencyContactController {

    private final PatientEmergencyContactService contactService;
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST','PATIENT')")
    @PostMapping("/patients/emergency/contacts/{patientId}")
    public ResponseEntity<EmergencyContactResponse> addContact(
            @PathVariable Long patientId,
            @RequestBody EmergencyContactRequest request) {

        request.setPatientId(patientId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contactService.addContact(request));
    }
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    @PutMapping("/patients/emergency/contacts/{contactId}")
    public ResponseEntity<EmergencyContactResponse> updateContact(
            @PathVariable Long contactId,
            @RequestBody EmergencyContactRequest request) {

        return ResponseEntity.ok(
                contactService.updateContact(contactId, request));
    }
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST','PATIENT')")
    @GetMapping("/patients/emergency/contacts/{patientId}")
    public ResponseEntity<List<EmergencyContactResponse>> getContacts(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                contactService.getContactsByPatient(patientId));
    }
}

