package com.projecth.hms.inventory.controller;

import com.projecth.hms.inventory.dto.InventoryItemRequest;
import com.projecth.hms.inventory.dto.InventoryItemResponse;
import com.projecth.hms.inventory.service.InventoryItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/inventory/items")
    public ResponseEntity<InventoryItemResponse> create(
            @RequestBody InventoryItemRequest request) {
        return ResponseEntity.ok(inventoryItemService.create(request));
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/inventory/items/{id}")
    public ResponseEntity<InventoryItemResponse> update(
            @PathVariable Long id,
            @RequestBody InventoryItemRequest request) {
        return ResponseEntity.ok(inventoryItemService.update(id, request));
    }
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    @GetMapping("/inventory/items/{id}")
    public ResponseEntity<InventoryItemResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryItemService.getById(id));
    }
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    @GetMapping("/inventory/items")
    public ResponseEntity<List<InventoryItemResponse>> getAll() {
        return ResponseEntity.ok(inventoryItemService.getAll());
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/inventory/items/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        inventoryItemService.deactivate(id);
        return ResponseEntity.ok().build();
    }
}

