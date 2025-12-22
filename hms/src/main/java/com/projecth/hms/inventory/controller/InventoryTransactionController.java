package com.projecth.hms.inventory.controller;

import com.projecth.hms.inventory.dto.InventoryTransactionRequest;
import com.projecth.hms.inventory.service.InventoryTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InventoryTransactionController {

    private final InventoryTransactionService transactionService;
    @PreAuthorize("hasAnyRole('PHARMACIST')")
    @PostMapping("/inventory/transactions")
    public ResponseEntity<String> createTransaction(
            @RequestBody InventoryTransactionRequest request) {

        transactionService.createTransaction(request);
        return ResponseEntity.ok("Inventory transaction recorded successfully");
    }
}

