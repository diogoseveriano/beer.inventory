package records;

import enums.InventoryType;
import io.smallrye.common.constraint.NotNull;
import model.Supplier;
import model.Warehouse;

import java.math.BigDecimal;
import java.util.Date;

public record InventoryManualRequest(Integer itemId, Integer itemVariantId, Supplier supplier, Warehouse warehouse,
                                     InventoryType inventoryType, double quantity,
                                     BigDecimal costPrice, String notes, String batch, @NotNull Date entryDate,
                                     BigDecimal retailPrice, BigDecimal salePrice) {
}
