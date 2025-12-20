package com.projecth.hms.doctor.service;

import com.projecth.hms.address.dto.AddressResponse;
import com.projecth.hms.address.entity.Address;
import com.projecth.hms.address.service.AddressService;
import com.projecth.hms.department.repository.DepartmentRepository;
import com.projecth.hms.doctor.dto.DoctorRequest;
import com.projecth.hms.doctor.dto.DoctorResponse;
import com.projecth.hms.doctor.entity.Doctor;
import com.projecth.hms.doctor.repository.DoctorRepository;
import com.projecth.hms.shared.enums.DoctorStatus;
import com.projecth.hms.shared.mapper.AddressMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor


public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final AddressService addressService;
    private final DepartmentRepository departmentRepository;


    @Transactional
    public DoctorResponse addDoctor(DoctorRequest request) {
        if (!departmentRepository.existsById(request.getDepartmentId())) {
            throw new RuntimeException("Invalid department id");
        }
        Address address =
                addressService.addAddress(request.getAddress());

        Doctor doctor = new Doctor();
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setPhone(request.getPhone());
        doctor.setEmail(request.getEmail());
        doctor.setExperience(request.getExperience());
        doctor.setDepartmentId(request.getDepartmentId());
        doctor.setStatus(DoctorStatus.ACTIVE);
        doctor.setAddressId(address.getId());
        doctor.setDepartmentId(request.getDepartmentId());

        Doctor savedDoctor = doctorRepository.save(doctor);

        return mapToResponse(savedDoctor, address);
    }

    //  UPDATE DOCTOR + ADDRESS
    public DoctorResponse updateDoctor(Long doctorId, DoctorRequest request) {
        if (!departmentRepository.existsById(request.getDepartmentId())) {
            throw new RuntimeException("Invalid department id");
        }
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found with id " + doctorId));

        Address address =
                addressService.updateAddress(
                        doctor.getAddressId(),
                        request.getAddress()
                );

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setPhone(request.getPhone());
        doctor.setEmail(request.getEmail());
        doctor.setExperience(request.getExperience());
        doctor.setDepartmentId(request.getDepartmentId());
        Doctor updatedDoctor = doctorRepository.save(doctor);

        return mapToResponse(updatedDoctor, address);
    }

    public DoctorResponse getDoctorById(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found with id " + doctorId));

        Address address =
                addressService.get(doctor.getAddressId());

        return mapToResponse(doctor, address);
    }
    @Transactional
    public void disableDoctor(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found with id " + doctorId));

        doctor.setStatus(DoctorStatus.INACTIVE);
        doctorRepository.save(doctor);
    }

    //Helper MAPPER
    private DoctorResponse mapToResponse(Doctor doctor, Address address) {

        return DoctorResponse.builder()
                .doctorId(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .specialization(doctor.getSpecialization())
                .phone(doctor.getPhone())
                .email(doctor.getEmail())
                .experience(doctor.getExperience())
                .departmentId(doctor.getDepartmentId())
                .status(doctor.getStatus())
                .address(AddressMapper.toResponse(address))
                .build();
    }
}

