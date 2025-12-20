package com.projecth.hms.patient.service;

import com.projecth.hms.patient.dto.PatientConsentRequest;
import com.projecth.hms.patient.dto.PatientConsentResponse;
import com.projecth.hms.patient.entity.PatientConsent;
import com.projecth.hms.patient.repository.PatientConsentRepository;
import com.projecth.hms.patient.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientConsentService {

    private final PatientConsentRepository patientConsentRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public PatientConsentResponse createPatientConsent(PatientConsentRequest request) {

        patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        PatientConsent consent = new PatientConsent();
        consent.setPatientId(request.getPatientId());
        consent.setConsentType(request.getConsentType());
        consent.setConsentGiven(request.getConsentGiven());
        consent.setConsentGivenBy(request.getConsentGivenBy());
        consent.setGuardianName(request.getGuardianName());
        consent.setRemarks(request.getRemarks());
        consent.setConsentDate(LocalDateTime.now());
        consent.setCreatedAt(LocalDateTime.now());

        return mapToResponse(patientConsentRepository.save(consent));
    }

    public PatientConsentResponse getConsentById(Long consentId) {
        PatientConsent consent = patientConsentRepository.findById(consentId)
                .orElseThrow(() -> new RuntimeException("Consent not found"));
        return mapToResponse(consent);
    }

    public PatientConsentResponse getLatestConsentByPatient(Long patientId) {
        PatientConsent consent = patientConsentRepository
                .findTopByPatientIdOrderByConsentDateDesc(patientId)
                .orElseThrow(() -> new RuntimeException("No consent found for patient"));
        return mapToResponse(consent);
    }

    public List<PatientConsentResponse> getConsentHistory(Long patientId) {
        return patientConsentRepository
                .findAllByPatientIdOrderByConsentDateDesc(patientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private PatientConsentResponse mapToResponse(PatientConsent consent) {
        return PatientConsentResponse.builder()
                .patientConsentId(consent.getId())
                .patientId(consent.getPatientId())
                .consentType(consent.getConsentType())
                .consentGiven(consent.getConsentGiven())
                .consentGivenBy(consent.getConsentGivenBy())
                .guardianName(consent.getGuardianName())
                .remarks(consent.getRemarks())
                .consentDate(consent.getConsentDate())
                .createdAt(consent.getCreatedAt())
                .build();
    }
}
