package records;

public record ItemRequest(String code, String name, Integer categoryId, String brand, String description,
                          String notes, boolean deprecated) {
}
