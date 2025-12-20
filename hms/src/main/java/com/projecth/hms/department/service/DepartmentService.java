package com.projecth.hms.department.service;

import com.projecth.hms.department.dto.DepartmentRequest;
import com.projecth.hms.department.dto.DepartmentResponse;
import com.projecth.hms.department.entity.Department;
import com.projecth.hms.department.repository.DepartmentRepository;
import com.projecth.hms.shared.enums.DepartmentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;


    public DepartmentResponse createDepartment(DepartmentRequest request) {
        if (departmentRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Department code already exists");
        }

        Department department = new Department();
        department.setName(request.getName());
        department.setCode(request.getCode());
        department.setStatus(DepartmentStatus.ACTIVE);

        return mapToResponse(departmentRepository.save(department));
    }

    public DepartmentResponse updateDepartment(Long departmentId,DepartmentRequest request) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(()-> new RuntimeException("Department Id not found "));
        if (departmentRepository.existsByCode(request.getCode())) {
            throw new RuntimeException("Department code already exists");
        }

//        Department department = new Department();
        department.setName(request.getName());
        department.setCode(request.getCode());
        department.setStatus(DepartmentStatus.ACTIVE);

        return mapToResponse(departmentRepository.save(department));
    }
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public DepartmentResponse getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return mapToResponse(department);
    }

    public DepartmentResponse deactivateDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        department.setStatus(DepartmentStatus.INACTIVE);
        return mapToResponse(departmentRepository.save(department));
    }

    private DepartmentResponse mapToResponse(Department department) {
        DepartmentResponse response = new DepartmentResponse();
        response.setId(department.getId());
        response.setName(department.getName());
        response.setCode(department.getCode());
        response.setStatus(department.getStatus());
        return response;
    }
}

