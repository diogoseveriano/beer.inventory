package service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import model.Inventory;
import records.InventoryManualRequest;

import java.util.List;

@ApplicationScoped
public class InventoryService {

    @Inject
    ItemService itemService;

    @Inject
    UnitService unitService;

    @Transactional
    public boolean createManualEntryOnInventory(InventoryManualRequest request) {
        Inventory.builder()
                .item(itemService.findById(request.itemId()))
                .inventoryType(request.inventoryType())
                .unit(unitService.findById(request.unitId()))
                .alertLowStock(request.alertLowStock())
                .quantity(request.quantity())
                .minQuantity(request.minQuantity())
                .costPrice(request.costPrice())
                .build().persistAndFlush();

        return true;
    }

    public List<Inventory> findAll() {
        return Inventory.findAll().list();
    }

}
