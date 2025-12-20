package com.projecth.hms.inventory.controller;

import com.projecth.hms.inventory.dto.InventoryItemRequest;
import com.projecth.hms.inventory.dto.InventoryItemResponse;
import com.projecth.hms.inventory.service.InventoryItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;

    @PostMapping("/inventory/items")
    public ResponseEntity<InventoryItemResponse> create(
            @RequestBody InventoryItemRequest request) {
        return ResponseEntity.ok(inventoryItemService.create(request));
    }

    @PutMapping("/inventory/items/{id}")
    public ResponseEntity<InventoryItemResponse> update(
            @PathVariable Long id,
            @RequestBody InventoryItemRequest request) {
        return ResponseEntity.ok(inventoryItemService.update(id, request));
    }

    @GetMapping("/inventory/items/{id}")
    public ResponseEntity<InventoryItemResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryItemService.getById(id));
    }

    @GetMapping("/inventory/items")
    public ResponseEntity<List<InventoryItemResponse>> getAll() {
        return ResponseEntity.ok(inventoryItemService.getAll());
    }

    @DeleteMapping("/inventory/items/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        inventoryItemService.deactivate(id);
        return ResponseEntity.ok().build();
    }
}

