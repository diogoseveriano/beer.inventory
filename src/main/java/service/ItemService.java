package service;

import enums.ItemType;
import exceptions.ItemAlreadyExists;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import model.Inventory;
import model.Item;
import model.ItemVariant;
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
                .description(item.description())
                .notes(item.notes())
                .deprecated(item.deprecated())
                .build().persistAndFlush();

        return true;
    }

    @Transactional
    public void updateQuantity(Inventory inventory, Integer variantId) {
        ItemVariant variant = ItemVariant.findById(variantId);
        variant.setQuantity(variant.getQuantity() + inventory.getQuantity());
        variant.persist();
    }

    @Transactional
    public void updateCosts(Inventory inventory, Integer variantId, BigDecimal retailPrice, BigDecimal salePrice) {
        ItemVariant variant = ItemVariant.findById(variantId);
        if (retailPrice != null)
            variant.setRetailPrice(retailPrice);
        if (salePrice != null)
            variant.setSalePrice(salePrice);
        variant.setIndicativeCostPrice(inventory.getCostPrice());
        variant.persist();
    }

    public List<Item> findAllInventory() {
        return Item.find("itemType", ItemType.INVENTORY).list();
    }

    public List<Item> findAllStock() {
        return Item.find("itemType", ItemType.STOCK).list();
    }

    public Item findById(Integer id) {
        return Item.findById(id);
    }

    public ItemVariant findVariantById(Integer id) { return ItemVariant.findById(id); }
}
