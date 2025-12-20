package com.projecth.hms.inventory.repository;

import com.projecth.hms.inventory.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemRepository
        extends JpaRepository<InventoryItem, Long> {

    boolean existsByNameIgnoreCase(String name);
}

