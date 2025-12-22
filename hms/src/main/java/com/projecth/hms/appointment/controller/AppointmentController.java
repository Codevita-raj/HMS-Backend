package com.projecth.hms.appointment.controller;

import com.projecth.hms.appointment.dto.AppointmentRequest;
import com.projecth.hms.appointment.dto.AppointmentResponse;
import com.projecth.hms.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    @PreAuthorize("hasAnyRole('PATIENT','RECEPTIONIST')")
    @PostMapping("/appointments")
    public AppointmentResponse create(@Valid @RequestBody AppointmentRequest request) {
        return appointmentService.createAppointment(request);
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST','PATIENT')")
    @GetMapping("/appointments/{id}")
    public AppointmentResponse getById(@PathVariable Long id) {
        return appointmentService.getAppointment(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    @GetMapping("/appointments/patient/{patientId}")
    public List<AppointmentResponse> getByPatient(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/appointments/doctor/{doctorId}")
    public List<AppointmentResponse> getByDoctor(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }
    @PreAuthorize("hasAnyRole('RECEPTIONIST')")
    @PutMapping("/appointments/{id}")
    public AppointmentResponse update(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequest request
    ) {
        return appointmentService.updateAppointment(id, request);
    }
    @PreAuthorize("hasAnyRole('RECEPTIONIST','PATIENT')")
    @DeleteMapping("/appointments/{id}")
    public void cancel(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
    }
}

