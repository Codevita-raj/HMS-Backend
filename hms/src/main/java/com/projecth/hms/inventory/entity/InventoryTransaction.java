package com.projecth.hms.inventory.entity;

import com.projecth.hms.shared.enums.InventoryTransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_transaction")
@Getter
@Setter
public class InventoryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // WHAT
    @Column(nullable = false)
    private Long itemId;

    // WHERE
    @Column(nullable = false)
    private Long locationId;

    // WHY
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InventoryTransactionType transactionType;

    // HOW MUCH
    @Column(nullable = false)
    private Integer quantity;

    // Optional references
    private Long patientId;
    private Long admissionId;
    private Long referenceId; // purchaseId / issueId etc.

    private String remarks;

    private LocalDateTime transactionAt;

    private String createdBy;
}

