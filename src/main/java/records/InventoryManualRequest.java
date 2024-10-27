package records;

import enums.InventoryType;

import java.math.BigDecimal;

public record InventoryManualRequest(Integer itemId, InventoryType inventoryType, int quantity,
                                     int minQuantity, boolean alertLowStock, Integer unitId,
                                     BigDecimal costPrice, String notes) {
}
