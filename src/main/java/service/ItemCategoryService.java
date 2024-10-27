package service;

import exceptions.ItemCategoryAlreadyExists;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import model.ItemCategory;
import records.ItemCategoryRecord;

import java.util.List;

@ApplicationScoped
public class ItemCategoryService {

    @Transactional
    public boolean createItemCategory(@NotNull ItemCategoryRecord item) {
        if (ItemCategory.find("name = ?1", item.name()).count() > 0)
            throw new ItemCategoryAlreadyExists(item.name());

        ItemCategory.builder()
                .isActive(item.isActive())
                .name(item.name())
                .description(item.description())
                .build().persistAndFlush();

        return true;
    }

    public List<ItemCategory> findAll() {
        return ItemCategory.findAll().list();
    }

    public ItemCategory findById(Integer id) {
        return ItemCategory.findById(id);
    }

}
