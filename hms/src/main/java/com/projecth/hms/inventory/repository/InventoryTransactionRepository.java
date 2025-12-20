package com.projecth.hms.inventory.repository;

import com.projecth.hms.inventory.entity.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {

    List<InventoryTransaction> findByItemId(Long itemId);

    List<InventoryTransaction> findByLocationId(Long locationId);

    List<InventoryTransaction> findByAdmissionId(Long admissionId);
}

