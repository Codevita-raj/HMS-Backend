package com.projecth.hms.inventory.repository;

import com.projecth.hms.inventory.entity.InventoryStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface InventoryStockRepository extends JpaRepository<InventoryStock, Long> {

    Optional<InventoryStock> findByItemIdAndLocationId(Long itemId, Long locationId);

    List<InventoryStock> findByLocationId(Long locationId);
}

