package com.projecth.hms.staff.controller;

import com.projecth.hms.shared.enums.StaffStatus;
import com.projecth.hms.staff.dto.StaffRequest;
import com.projecth.hms.staff.dto.StaffResponse;
import com.projecth.hms.staff.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StaffController {

    private final StaffService staffService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/staff")
    public ResponseEntity<StaffResponse> create(
            @RequestBody StaffRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(staffService.createStaff(request));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/staff/{id}")
    public ResponseEntity<StaffResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(staffService.getStaffById(id));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/staff")
    public ResponseEntity<List<StaffResponse>> getAll() {
        return ResponseEntity.ok(staffService.getAllStaff());
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/staff/{id}")
    public ResponseEntity<StaffResponse> update(
            @PathVariable Long id,
            @RequestBody StaffRequest request) {
        return ResponseEntity.ok(staffService.updateStaff(id, request));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/staff/{id}/status")
    public ResponseEntity<StaffResponse> changeStatus(
            @PathVariable Long id,
            @RequestParam StaffStatus status) {
        return ResponseEntity.ok(staffService.changeStatus(id, status));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/staff/{id}")
    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
        staffService.softDeleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}

