package com.projecth.hms.inventory.dto;

import com.projecth.hms.shared.enums.InventoryCategory;
import com.projecth.hms.shared.enums.InventoryUnit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryItemRequest {
    private String name;
    private InventoryCategory category;
    private InventoryUnit unit;
    private String description;
}

