package exceptions;

public class ItemCategoryAlreadyExists extends RuntimeException {

    public ItemCategoryAlreadyExists(String name) {
        super(String.format("Item Category already exists with name %s", name));
    }

}
