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

import java.math.BigDecimal;
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
                .unit(item.unit())
                .description(item.description())
                .notes(item.notes())
                .deprecated(item.deprecated())
                .minQuantity(item.minQuantity())
                .quantity(item.quantity())
                .alertLowStock(item.alertLowStock())
                .retailPrice(item.retailPrice())
                .salePrice(item.salePrice())
                .build().persistAndFlush();

        return true;
    }

    @Transactional
    public void updateQuantity(Inventory inventory) {
        inventory.getItem().setQuantity(inventory.getItem().getQuantity() + inventory.getQuantity());
        inventory.getItem().persist();
    }

    @Transactional
    public void updateCosts(Inventory inventory, BigDecimal retailPrice, BigDecimal salePrice) {
        Item item = inventory.getItem();
        if (retailPrice != null)
            item.setRetailPrice(retailPrice);
        if (salePrice != null)
            item.setSalePrice(salePrice);
        item.setIndicativeCostPrice(inventory.getCostPrice());
        item.persist();
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
