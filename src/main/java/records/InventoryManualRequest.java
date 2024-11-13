package records;

import enums.InventoryType;
import model.Supplier;
import model.Warehouse;

import java.math.BigDecimal;
import java.util.Date;

public record InventoryManualRequest(Integer itemId, Supplier supplier, Warehouse warehouse,
                                     InventoryType inventoryType, double quantity, Integer unitId,
                                     BigDecimal costPrice, String notes, String batch, BigDecimal salePrice,
                                     Date entryDate) {
}
