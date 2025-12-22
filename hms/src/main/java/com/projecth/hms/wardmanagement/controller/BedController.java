package com.projecth.hms.wardmanagement.controller;

import com.projecth.hms.wardmanagement.dto.bed.BedRequest;
import com.projecth.hms.wardmanagement.dto.bed.BedResponse;
import com.projecth.hms.wardmanagement.service.BedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BedController {

    private final BedService service;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/beds")
    public ResponseEntity<BedResponse> create(@RequestBody BedRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }
    @PreAuthorize("hasAnyRole('ADMIN','NURSE')")
    @GetMapping("/beds/{id}")
    public ResponseEntity<BedResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }
    @PreAuthorize("hasAnyRole('ADMIN','NURSE')")
    @GetMapping("/beds/ward/{wardId}")
    public ResponseEntity<List<BedResponse>> getByWard(@PathVariable Long wardId) {
        return ResponseEntity.ok(service.getByWard(wardId));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/beds/{id}")
    public ResponseEntity<BedResponse> update(
            @PathVariable Long id,
            @RequestBody BedRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/beds/{id}/maintenance")
    public ResponseEntity<Void> maintenance(@PathVariable Long id) {
        service.markMaintenance(id);
        return ResponseEntity.ok().build();
    }
}

