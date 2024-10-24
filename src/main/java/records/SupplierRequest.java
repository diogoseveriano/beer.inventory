package records;

public record SupplierRequest(String supplierName, String code, String shortDescription, Long nif,
                              String address, String postalCode, String city, String country,
                              String currency, String email, String phone, String contactPerson,
                              String contactPersonEmail, String contactPersonPhone, String notes) {
}
