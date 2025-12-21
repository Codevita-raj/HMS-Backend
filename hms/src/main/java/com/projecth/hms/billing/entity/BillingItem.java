package com.projecth.hms.billing.entity;

import com.projecth.hms.shared.enums.BillingItemType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "billing_item")
public class BillingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long billingId;

    @Enumerated(EnumType.STRING)
    private BillingItemType itemType;
    // MEDICINE, LAB, BED, PROCEDURE, SERVICE

    private Long referenceId;
    // medicineIssueId / labOrderItemId / bedAllocationId

    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal amount;
}

