package com.projecth.hms.doctor.controller;

import com.projecth.hms.doctor.dto.DoctorRequest;
import com.projecth.hms.doctor.dto.DoctorResponse;
import com.projecth.hms.doctor.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/doctors")
    public ResponseEntity<DoctorResponse> addDoctor(
            @RequestBody DoctorRequest request
    ) {
        DoctorResponse response = doctorService.addDoctor(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<DoctorResponse> getDoctorById(
            @PathVariable Long doctorId
    ) {
        DoctorResponse response = doctorService.getDoctorById(doctorId);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/doctor/{doctorId}")
    public ResponseEntity<DoctorResponse> updateDoctor(
            @PathVariable Long doctorId,
            @RequestBody DoctorRequest request
    ) {
        DoctorResponse response =
                doctorService.updateDoctor(doctorId, request);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/doctor/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(
            @PathVariable Long doctorId
    ) {
        doctorService.disableDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }
}

