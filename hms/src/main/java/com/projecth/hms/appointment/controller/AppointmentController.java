package com.projecth.hms.appointment.controller;

import com.projecth.hms.appointment.dto.AppointmentRequest;
import com.projecth.hms.appointment.dto.AppointmentResponse;
import com.projecth.hms.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/appointments")
    public AppointmentResponse create(@Valid @RequestBody AppointmentRequest request) {
        return appointmentService.createAppointment(request);
    }

    @GetMapping("/appointments/{id}")
    public AppointmentResponse getById(@PathVariable Long id) {
        return appointmentService.getAppointment(id);
    }

    @GetMapping("/appointments/patient/{patientId}")
    public List<AppointmentResponse> getByPatient(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }

    @GetMapping("/appointments/doctor/{doctorId}")
    public List<AppointmentResponse> getByDoctor(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    @PutMapping("/appointments/{id}")
    public AppointmentResponse update(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequest request
    ) {
        return appointmentService.updateAppointment(id, request);
    }

    @DeleteMapping("/appointments/{id}")
    public void cancel(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
    }
}

