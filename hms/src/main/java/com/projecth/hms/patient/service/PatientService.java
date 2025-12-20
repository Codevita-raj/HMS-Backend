package com.projecth.hms.patient.service;

import com.projecth.hms.address.entity.Address;
import com.projecth.hms.address.repository.AddressRepository;
import com.projecth.hms.address.service.AddressService;
import com.projecth.hms.patient.dto.PatientRequest;
import com.projecth.hms.patient.dto.PatientResponse;
import com.projecth.hms.patient.entity.Patient;
import com.projecth.hms.patient.repository.PatientRepository;
import com.projecth.hms.shared.enums.PatientStatus;
import com.projecth.hms.shared.mapper.AddressMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final AddressRepository addressRepository;
    private final PatientRepository patientRepository;
    private final AddressService addressService;

    @Transactional
    public PatientResponse addPatient(PatientRequest patientRequest) {

        Address address = addressService.addAddress(patientRequest.getAddress());

        Patient patient = new Patient();
        patient.setEmail(patientRequest.getEmail());
        patient.setDob(patientRequest.getDob());
        patient.setGender(patientRequest.getGender());
        patient.setStatus(PatientStatus.ACTIVE);
        patient.setFirstName(patientRequest.getFirstName());
        patient.setLastName(patientRequest.getLastName());
        patient.setPhone(patientRequest.getPhone());
        patient.setAssignedDoctorId(patientRequest.getAssignedDoctorId());
        patient.setAddressId(address.getId());


        patientRepository.save(patient);

        Long id = patient.getId();
        int year = Year.now().getValue();
        String patientCode = "PAT-" +(year)+"-"+ (10000 + id);

        patient.setPatientCode(patientCode);
        patientRepository.save(patient);
        return mapToResponse(patient, address);
    }

    public PatientResponse getPatientDetails(Long patientId){
        Patient patientDetails = patientRepository.findById(patientId)
                .orElseThrow(()-> new RuntimeException("Patient not found with id "+patientId));
        Address address = addressService.get(patientDetails.getAddressId());
        return mapToResponse(patientDetails, address);
    }

    @Transactional
    public PatientResponse updatePatient(Long patientId,PatientRequest patientRequest){
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()-> new RuntimeException("Patient not found with id "+patientId));

        Address address = addressService.updateAddress(patient.getAddressId(), patientRequest.getAddress());

        patient.setFirstName(patientRequest.getFirstName());
        patient.setGender(patientRequest.getGender());
        patient.setLastName(patientRequest.getLastName());
        patient.setDob(patientRequest.getDob());
        patient.setPhone(patientRequest.getPhone());
        patient.setEmail(patientRequest.getEmail());
        patient.setAssignedDoctorId(patientRequest.getAssignedDoctorId());
      //  patient.setAddressId(address.getId());

        Patient savedPatient = patientRepository.save(patient);

        return mapToResponse(savedPatient, address);
    }

    @Transactional
    public void deletePatient(Long patientId){
        Patient deletePatient = patientRepository.findById(patientId)
                .orElseThrow(()-> new RuntimeException("Patient not found with id "+patientId));
        patientRepository.delete(deletePatient);

    }
    //Helper method
    private PatientResponse mapToResponse(Patient patient, Address address) {
        return PatientResponse.builder()
                .patientId(patient.getId())
                .patientCode(patient.getPatientCode())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .dob(patient.getDob())
                .gender(patient.getGender())
                .email(patient.getEmail())
                .phone(patient.getPhone())
                .status(patient.getStatus())
                .assignedDoctorId(patient.getAssignedDoctorId())
                .address(AddressMapper.toResponse(address))
                .build();
    }
}
