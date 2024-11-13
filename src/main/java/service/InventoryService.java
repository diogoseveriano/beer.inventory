package service;

import enums.InventoryType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import model.Inventory;
import records.InventoryManualRequest;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/// TODO :: CONTROLAR ENTRADAS E SAIDA DE PRODUTO ACABADO COM BATCH

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
        System.out.println(request);

        Inventory newEntry = Inventory.builder()
                .salePrice(request.salePrice())
                .supplier(request.supplier().id == -1 ? null : request.supplier())
                .batch(request.batch())
                .warehouse(request.warehouse())
                .item(itemService.findById(request.itemId()))
                .inventoryType(request.inventoryType())
                .unit(unitService.findById(request.unitId()))
                .quantity(request.quantity())
                .costPrice(request.costPrice())
                .entryDate(request.entryDate())
                .build();

        newEntry.persistAndFlush();
        itemService.updateQuantity(newEntry);

        return true;
    }

    //REMOVE
    public List<Inventory> findAll() {
        return Inventory.findAll().list();
    }

    public List<Inventory> findInventoryByWarehouse(long warehouse) {
        List<Inventory> inventoryList = Inventory.find("warehouse = ?1 and inventoryType <> ?2",
                warehouseService.getWarehouseById(warehouse), InventoryType.FINISHED_PRODUCT).list();
        return inventoryList.stream()
                .sorted(Comparator.comparing(Inventory::getEntryDate))
                .collect(Collectors.toList());
    }



    public List<Inventory> findStockByWarehouse(long warehouse) {
        return Inventory.find("warehouse = ?1 and inventoryType = ?2",
                warehouseService.getWarehouseById(warehouse), InventoryType.FINISHED_PRODUCT).list();
    }

}
