package com.projecth.hms.inventory.dto;

import com.projecth.hms.shared.enums.InventoryCategory;
import com.projecth.hms.shared.enums.InventoryUnit;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InventoryItemResponse {
    private Long id;
    private String name;
    private InventoryCategory category;
    private InventoryUnit unit;
    private String description;
    private Boolean active;
}

