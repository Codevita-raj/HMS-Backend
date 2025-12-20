package com.projecth.hms.inventory.service;

import com.projecth.hms.inventory.dto.InventoryItemRequest;
import com.projecth.hms.inventory.dto.InventoryItemResponse;
import com.projecth.hms.inventory.entity.InventoryItem;
import com.projecth.hms.inventory.repository.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryItemService {

    private final InventoryItemRepository inventoryItemRepository;

    public InventoryItemResponse create(InventoryItemRequest request) {

        if (inventoryItemRepository.existsByNameIgnoreCase(request.getName())) {
            throw new RuntimeException("Inventory item already exists");
        }

        InventoryItem item = new InventoryItem();
        item.setName(request.getName());
        item.setCategory(request.getCategory());
        item.setUnit(request.getUnit());
        item.setDescription(request.getDescription());
        item.setActive(true);
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(inventoryItemRepository.save(item));
    }

    public InventoryItemResponse update(Long id, InventoryItemRequest request) {

        InventoryItem item = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found"));

        item.setName(request.getName());
        item.setCategory(request.getCategory());
        item.setUnit(request.getUnit());
        item.setDescription(request.getDescription());
        item.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(inventoryItemRepository.save(item));
    }

    public InventoryItemResponse getById(Long id) {
        return inventoryItemRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Inventory item not found"));
    }

    public List<InventoryItemResponse> getAll() {
        return inventoryItemRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void deactivate(Long id) {
        InventoryItem item = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found"));

        item.setActive(false);
        item.setUpdatedAt(LocalDateTime.now());
        inventoryItemRepository.save(item);
    }

    private InventoryItemResponse mapToResponse(InventoryItem item) {
        return InventoryItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .category(item.getCategory())
                .unit(item.getUnit())
                .description(item.getDescription())
                .active(item.getActive())
                .build();
    }
}

