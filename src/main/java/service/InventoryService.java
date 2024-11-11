package service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import model.Inventory;
import model.Warehouse;
import records.InventoryManualRequest;

import java.util.List;

@ApplicationScoped
public class InventoryService {

    @Inject
    ItemService itemService;

    @Inject
    UnitService unitService;

    @Inject
    WarehouseService warehouseService;

    @Transactional
    public boolean createManualEntryOnInventory(InventoryManualRequest request) {
        Inventory.builder()
                .salePrice(request.salePrice())
                .supplier(request.supplier())
                .batch(request.batch())
                .warehouse(request.warehouse())
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

    //REMOVE
    public List<Inventory> findAll() {
        return Inventory.findAll().list();
    }

    public List<Inventory> findAllByWarehouse(long warehouse) {
        return Inventory.find("warehouse", warehouseService.getWarehouseById(warehouse)).list();
    }

}
