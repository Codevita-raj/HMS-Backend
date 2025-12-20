package com.projecth.hms.inventory.dto;

import com.projecth.hms.shared.enums.InventoryTransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryTransactionRequest {

    private Long itemId;
    private Long locationId;
    private InventoryTransactionType transactionType;
    private Integer quantity;

    private Long patientId;
    private Long admissionId;
    private Long referenceId;

    private String remarks;
}

