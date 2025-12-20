package com.projecth.hms.appointment.service;

import com.projecth.hms.appointment.dto.AppointmentRequest;
import com.projecth.hms.appointment.dto.AppointmentResponse;
import com.projecth.hms.appointment.entity.Appointment;
import com.projecth.hms.appointment.repository.AppointmentRepository;
import com.projecth.hms.doctor.entity.Doctor;
import com.projecth.hms.doctor.repository.DoctorRepository;
import com.projecth.hms.patient.entity.Patient;
import com.projecth.hms.patient.repository.PatientRepository;
import com.projecth.hms.shared.enums.AppointmentStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    // CREATE
    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request) {

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with ID " + request.getPatientId()));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID " + request.getDoctorId()));


        boolean alreadyBooked = appointmentRepository
                .existsByDoctorIdAndAppointmentDateAndAppointmentTimeAndStatus(
                        request.getDoctorId(),
                        request.getAppointmentDate(),
                        request.getAppointmentTime(),
                        AppointmentStatus.BOOKED
                );

        if (alreadyBooked) {
            throw new RuntimeException("Doctor already has an appointment at this time");
        }

        Appointment appointment = Appointment.builder()
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .appointmentDate(request.getAppointmentDate())
                .appointmentTime(request.getAppointmentTime())
                .reason(request.getReason())
                .status(AppointmentStatus.BOOKED)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Appointment saved = appointmentRepository.save(appointment);
        return mapToResponse(saved);
    }

    // GET BY ID
    public AppointmentResponse getAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        return mapToResponse(appointment);
    }

    // GET BY PATIENT
    public List<AppointmentResponse> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY DOCTOR
    public List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // UPDATE
    @Transactional
    public AppointmentResponse updateAppointment(Long appointmentId, AppointmentRequest request) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setUpdatedAt(LocalDateTime.now());

        Appointment saved = appointmentRepository.save(appointment);
        return mapToResponse(saved);
    }

    // CANCEL
    @Transactional
    public void cancelAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    // HELPER
    private AppointmentResponse mapToResponse(Appointment appointment) {
        return AppointmentResponse.builder()
                .appointmentId(appointment.getId())
                .patientId(appointment.getPatientId())
                .doctorId(appointment.getDoctorId())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .reason(appointment.getReason())
                .status(appointment.getStatus())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(appointment.getUpdatedAt())
                .build();
    }
}
