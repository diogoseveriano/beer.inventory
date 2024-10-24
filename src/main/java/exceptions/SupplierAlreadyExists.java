package exceptions;

public class SupplierAlreadyExists extends RuntimeException {

    public SupplierAlreadyExists(String code) {
        super(String.format("Supplier already exists with code %s", code));
    }

}
