package com.projecth.hms.inventory.entity;

import com.projecth.hms.shared.enums.InventoryCategory;
import com.projecth.hms.shared.enums.InventoryUnit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "inventory_item")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private InventoryCategory category;

    @Enumerated(EnumType.STRING)
    private InventoryUnit unit;

    private String description;

    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

