package service;

import enums.ItemType;
import exceptions.ItemAlreadyExists;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import model.Inventory;
import model.Item;
import records.ItemRequest;

import java.util.List;

@ApplicationScoped
public class ItemService {

    @Inject
    ItemCategoryService itemCategoryService;

    @Transactional
    public boolean createItem(@NotNull ItemRequest item) {
        if (Item.find("code = ?1", item.code()).count() > 0)
            throw new ItemAlreadyExists(item.code());

        Item.builder()
                .itemType(item.itemType())
                .code(item.code())
                .name(item.name())
                .brand(item.brand())
                .category(itemCategoryService.findById(item.categoryId()))
                .description(item.description())
                .notes(item.notes())
                .deprecated(item.deprecated())
                .minQuantity(item.minQuantity())
                .quantity(item.quantity())
                .alertLowStock(item.alertLowStock())
                .build().persistAndFlush();

        return true;
    }

    @Transactional
    public void updateQuantity(Inventory inventory) {
        inventory.getItem().setQuantity(inventory.getItem().getQuantity() + inventory.getQuantity());
        inventory.getItem().persistAndFlush();
    }

    public List<Item> findAllInventory() {
        return Item.find("itemType", ItemType.INVENTORY).list();
    }
    public List<Item> findAllStock() {
        return Item.find("itemType", ItemType.STOCK).list();
    }

    public Item  findById(Integer id) {
        return Item.findById(id);
    }

}
