package com.projecth.hms.inventory.repository;

import com.projecth.hms.inventory.entity.InventoryTransaction;
import com.projecth.hms.shared.enums.InventoryTransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {

    @Query("""
SELECT COALESCE(SUM(t.quantity), 0)
FROM InventoryTransaction t
WHERE t.admissionId = :admissionId
AND t.transactionType = :type
""")
    int sumQuantityByAdmissionAndType(
            Long admissionId,
            InventoryTransactionType type
    );


}

