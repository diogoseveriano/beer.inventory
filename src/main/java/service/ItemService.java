package service;

import exceptions.ItemAlreadyExists;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

    public List<Item> findAll() {
        return Item.findAll().list();
    }

    public Item findById(Integer id) {
        return Item.find("code = ?1", id).firstResult();
    }

}
