package records;

import enums.InventoryType;
import model.Warehouse;

import java.math.BigDecimal;

public record InventoryManualRequest(Integer itemId, Warehouse warehouse, InventoryType inventoryType, int quantity,
                                     int minQuantity, boolean alertLowStock, Integer unitId,
                                     BigDecimal costPrice, String notes) {
}
