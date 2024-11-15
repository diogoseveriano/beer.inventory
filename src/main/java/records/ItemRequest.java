package records;

import enums.ItemType;
import model.Unit;

import java.math.BigDecimal;

public record ItemRequest(String code, String name, Integer categoryId, String brand, String description,
                          String notes, ItemType itemType, double quantity, double minQuantity, boolean alertLowStock,
                          BigDecimal salePrice, BigDecimal retailPrice, Unit unit, boolean deprecated) {
}
