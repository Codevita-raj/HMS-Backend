package com.projecth.hms.lab.controller;

import com.projecth.hms.lab.dto.labOrder.LabOrderRequest;
import com.projecth.hms.lab.dto.labOrder.LabOrderResponse;
import com.projecth.hms.lab.service.LabOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LabOrderController {

    private final LabOrderService labOrderService;

    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PostMapping("/lab/orders")
    public ResponseEntity<LabOrderResponse> create(
            @RequestBody LabOrderRequest request) {
        return ResponseEntity.ok(labOrderService.createLabOrder(request));
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','LAB_TECHNICIAN')")
    @GetMapping("/lab/orders/{id}")
    public ResponseEntity<LabOrderResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(labOrderService.getLabOrderById(id));
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','LAB_TECHNICIAN')")
    @GetMapping("/lab/orders/patient/{patientId}")
    public ResponseEntity<List<LabOrderResponse>> getByPatient(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(
                labOrderService.getLabOrdersByPatientId(patientId));
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','LAB_TECHNICIAN')")
    @GetMapping("/lab/orders/admission/{admissionId}")
    public ResponseEntity<List<LabOrderResponse>> getByAdmission(
            @PathVariable Long admissionId) {
        return ResponseEntity.ok(
                labOrderService.getLabOrdersByAdmissionId(admissionId));
    }
    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PatchMapping("/lab/orders/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        labOrderService.cancelLabOrder(id);
        return ResponseEntity.ok().build();
    }
}


