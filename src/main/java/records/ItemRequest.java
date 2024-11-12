package records;

import enums.ItemType;

public record ItemRequest(String code, String name, Integer categoryId, String brand, String description,
                          String notes, ItemType itemType, double quantity, double minQuantity, boolean alertLowStock,
                          boolean deprecated) {
}
