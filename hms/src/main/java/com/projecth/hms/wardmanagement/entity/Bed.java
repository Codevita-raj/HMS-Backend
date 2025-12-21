package com.projecth.hms.wardmanagement.entity;

import com.projecth.hms.shared.enums.BedStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "beds")
@Getter
@Setter
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bedNumber;

    private BigDecimal dailyCharge;

    @Enumerated(EnumType.STRING)
    private BedStatus status;

    private Long wardId;
}

