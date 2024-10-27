package exceptions;

public class ItemAlreadyExists extends RuntimeException {

    public ItemAlreadyExists(String code) {
        super(String.format("Item already exists with code %s", code));
    }

}
