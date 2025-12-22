package com.projecth.hms.billing.entity;

import com.projecth.hms.shared.enums.BillStatus;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "billing")
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;
    private Long admissionId;

    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal netPayable;

    @Enumerated(EnumType.STRING)
    private BillStatus status; // DRAFT, GENERATED, PAID, CANCELLED

    private LocalDateTime generatedAt;
    private String generatedBy;
}

