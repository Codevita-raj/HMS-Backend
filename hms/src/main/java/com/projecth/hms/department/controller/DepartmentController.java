package com.projecth.hms.department.controller;

import com.projecth.hms.department.dto.DepartmentRequest;
import com.projecth.hms.department.dto.DepartmentResponse;
import com.projecth.hms.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/departments")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse create(@RequestBody DepartmentRequest request) {
        return departmentService.createDepartment(request);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/departments/{departmentId}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentResponse update(@PathVariable Long departmentId, @RequestBody DepartmentRequest request) {
        return departmentService.updateDepartment(departmentId,request);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/departments")
    public List<DepartmentResponse> getAll() {
        return departmentService.getAllDepartments();
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/departments/{id}")
    public DepartmentResponse getById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/departments/{id}/deactivate")
    public DepartmentResponse deactivate(@PathVariable Long id) {
        return departmentService.deactivateDepartment(id);
    }
}

