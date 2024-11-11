package records;

import enums.InventoryType;
import model.Supplier;
import model.Warehouse;

import java.math.BigDecimal;

public record InventoryManualRequest(Integer itemId, Supplier supplier, Warehouse warehouse,
                                     InventoryType inventoryType, int quantity,
                                     int minQuantity, boolean alertLowStock, Integer unitId,
                                     BigDecimal costPrice, String notes, String batch, BigDecimal salePrice) {
}
