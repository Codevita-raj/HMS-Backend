package com.projecth.hms.billing.dto;

import com.projecth.hms.shared.enums.BillingItemType;
import lombok.*;

import java.math.BigDecimal;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingItemDTO {
    private BillingItemType itemType;
    private Long referenceId;
    private String description;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal amount;
}

