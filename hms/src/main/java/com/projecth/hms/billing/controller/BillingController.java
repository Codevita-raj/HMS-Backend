package com.projecth.hms.billing.controller;

import com.projecth.hms.billing.entity.Billing;
import com.projecth.hms.billing.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;


    @PostMapping("billing/generate")
    public ResponseEntity<Billing> generateBilling(
            @RequestParam Long admissionId,
            @RequestParam Long patientId
    ) {
        Billing billing = billingService.generateBilling(admissionId, patientId);
        return ResponseEntity.ok(billing);
    }


    @GetMapping("/billing")
    public ResponseEntity<List<Billing>> getAllBills() {
        List<Billing> bills = billingService.getAllBills();
        return ResponseEntity.ok(bills);
    }


    @GetMapping("/billing/{billingId}")
    public ResponseEntity<Billing> getBillingById(@PathVariable Long billingId) {
        Billing billing = billingService.getBillingById(billingId);
        return ResponseEntity.ok(billing);
    }


    @PatchMapping("/billing/{billingId}/pay")
    public ResponseEntity<String> markBillAsPaid(@PathVariable Long billingId) {
        billingService.markBillAsPaid(billingId);
        return ResponseEntity.ok("Billing marked as PAID for id: " + billingId);
    }
}
