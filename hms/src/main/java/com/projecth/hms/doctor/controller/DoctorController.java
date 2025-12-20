package com.projecth.hms.doctor.controller;

import com.projecth.hms.doctor.dto.DoctorRequest;
import com.projecth.hms.doctor.dto.DoctorResponse;
import com.projecth.hms.doctor.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/doctors")
    public ResponseEntity<DoctorResponse> addDoctor(
            @RequestBody DoctorRequest request
    ) {
        DoctorResponse response = doctorService.addDoctor(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<DoctorResponse> getDoctorById(
            @PathVariable Long doctorId
    ) {
        DoctorResponse response = doctorService.getDoctorById(doctorId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/doctor/{doctorId}")
    public ResponseEntity<DoctorResponse> updateDoctor(
            @PathVariable Long doctorId,
            @RequestBody DoctorRequest request
    ) {
        DoctorResponse response =
                doctorService.updateDoctor(doctorId, request);
        return ResponseEntity.ok(response);
    }

    //  SOFT DELETE (STATUS CHANGE)
    @DeleteMapping("/doctor/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(
            @PathVariable Long doctorId
    ) {
        doctorService.disableDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }
}

