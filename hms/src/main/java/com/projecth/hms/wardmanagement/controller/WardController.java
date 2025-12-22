package com.projecth.hms.wardmanagement.controller;

import com.projecth.hms.wardmanagement.dto.ward.WardRequest;
import com.projecth.hms.wardmanagement.dto.ward.WardResponse;
import com.projecth.hms.wardmanagement.service.WardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WardController {

    private final WardService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/wards")
    public ResponseEntity<WardResponse> create(@RequestBody WardRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/wards/{id}")
    public ResponseEntity<WardResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/wards")
    public ResponseEntity<List<WardResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/wards/{id}")
    public ResponseEntity<WardResponse> update(
            @PathVariable Long id,
            @RequestBody WardRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/wards/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}

