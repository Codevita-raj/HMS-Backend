package com.projecth.hms.inventory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "inventory_stock",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"item_id", "location_id"})
        }
)
@Getter
@Setter
public class InventoryStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // WHAT
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    // WHERE
    @Column(name = "location_id", nullable = false)
    private Long locationId;

    // HOW MUCH
    @Column(nullable = false)
    private Integer availableQuantity;

    // optional but VERY useful
    private Integer reservedQuantity; // for future use (orders)

    private LocalDateTime lastUpdatedAt;
}

