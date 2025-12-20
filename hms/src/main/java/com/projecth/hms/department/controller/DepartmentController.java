package com.projecth.hms.department.controller;

import com.projecth.hms.department.dto.DepartmentRequest;
import com.projecth.hms.department.dto.DepartmentResponse;
import com.projecth.hms.department.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/departments")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse create(@RequestBody DepartmentRequest request) {
        return departmentService.createDepartment(request);
    }
    @PutMapping("/departments/{departmentId}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentResponse update(@PathVariable Long departmentId, @RequestBody DepartmentRequest request) {
        return departmentService.updateDepartment(departmentId,request);
    }
    @GetMapping("/departments")
    public List<DepartmentResponse> getAll() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/departments/{id}")
    public DepartmentResponse getById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PatchMapping("/departments/{id}/deactivate")
    public DepartmentResponse deactivate(@PathVariable Long id) {
        return departmentService.deactivateDepartment(id);
    }
}

