package com.projecth.hms.patient.service;

import com.projecth.hms.patient.dto.EmergencyContactRequest;
import com.projecth.hms.patient.dto.EmergencyContactResponse;
import com.projecth.hms.patient.entity.PatientEmergencyContact;
import com.projecth.hms.patient.repository.PatientEmergencyContactRepository;
import com.projecth.hms.patient.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientEmergencyContactService {

    private final PatientEmergencyContactRepository contactRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public EmergencyContactResponse addContact(EmergencyContactRequest request) {

        patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (request.getPrimaryContact() == true &&
                contactRepository.existsByPatientIdAndPrimaryContactTrue(request.getPatientId())) {
            throw new RuntimeException("Primary emergency contact already exists");
        }

        PatientEmergencyContact contact = new PatientEmergencyContact();
        contact.setPatientId(request.getPatientId());
        contact.setName(request.getName());
        contact.setRelation(request.getRelation());
        contact.setPhone(request.getPhone());
        contact.setAlternatePhone(request.getAlternatePhone());
        contact.setPrimaryContact(
                Boolean.TRUE.equals(request.getPrimaryContact())
        );
        contact.setCreatedAt(LocalDateTime.now());

        return mapToResponse(contactRepository.save(contact));
    }

    @Transactional
    public EmergencyContactResponse updateContact(
            Long contactId, EmergencyContactRequest request) {

        PatientEmergencyContact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        if (Boolean.TRUE.equals(request.getPrimaryContact())) {
            contactRepository.findByPatientIdAndPrimaryContactTrue(contact.getPatientId())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(contactId)) {
                            existing.setPrimaryContact(false);
                            existing.setUpdatedAt(LocalDateTime.now());
                            contactRepository.save(existing);
                        }
                    });
        }

        contact.setName(request.getName());
        contact.setRelation(request.getRelation());
        contact.setPhone(request.getPhone());
        contact.setAlternatePhone(request.getAlternatePhone());
        contact.setPrimaryContact(request.getPrimaryContact());
        contact.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(contactRepository.save(contact));
    }

    public List<EmergencyContactResponse> getContactsByPatient(Long patientId) {
        return contactRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private EmergencyContactResponse mapToResponse(PatientEmergencyContact c) {
        return EmergencyContactResponse.builder()
                .emergencyContactId(c.getId())
                .contactId(c.getId())
                .patientId(c.getPatientId())
                .name(c.getName())
                .relation(c.getRelation())
                .phone(c.getPhone())
                .alternatePhone(c.getAlternatePhone())
                .primaryContact(c.getPrimaryContact())

                .build();
    }
}

