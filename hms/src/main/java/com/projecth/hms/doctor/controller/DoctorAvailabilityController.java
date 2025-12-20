package com.projecth.hms.doctor.controller;

import java.util.List;

import com.projecth.hms.doctor.dto.DoctorAvailabilityRequest;
import com.projecth.hms.doctor.dto.DoctorAvailabilityResponse;
import com.projecth.hms.doctor.entity.DoctorAvailability;
import com.projecth.hms.doctor.service.DoctorAvailabilityService;
import com.projecth.hms.shared.mapper.DoctorAvailabilityMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService doctorAvailabilityService;

    @PostMapping("/doctors/availability")
    public ResponseEntity<DoctorAvailabilityResponse> createAvailability(
            @RequestBody DoctorAvailabilityRequest request
    ) {
        DoctorAvailability availability =
                DoctorAvailabilityMapper.toEntity(request);

        DoctorAvailability saved =
                doctorAvailabilityService.createAvailability(availability);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DoctorAvailabilityMapper.toResponse(saved));
    }

    @PutMapping("/doctors/availability/{availabilityId}")
    public ResponseEntity<DoctorAvailabilityResponse> updateAvailability(
            @PathVariable Long availabilityId,
            @RequestBody DoctorAvailabilityRequest request
    ) {
        DoctorAvailability updated =
                DoctorAvailabilityMapper.toEntity(request);

        DoctorAvailability saved =
                doctorAvailabilityService.updateAvailability(availabilityId, updated);

        return ResponseEntity.ok(
                DoctorAvailabilityMapper.toResponse(saved)
        );
    }

    @GetMapping("doctors/availability/doctor/{doctorId}")
    public ResponseEntity<List<DoctorAvailabilityResponse>> getByDoctor(
            @PathVariable Long doctorId
    ) {
        return ResponseEntity.ok(
                doctorAvailabilityService.getDoctorAvailability(doctorId)
                        .stream()
                        .map(DoctorAvailabilityMapper::toResponse)
                        .toList()
        );
    }

    @PatchMapping("/doctors/availability/{availabilityId}/deactivate")
    public ResponseEntity<Void> deactivateAvailability(
            @PathVariable Long availabilityId
    ) {
        doctorAvailabilityService.deactivateAvailability(availabilityId);
        return ResponseEntity.noContent().build();
    }
}

