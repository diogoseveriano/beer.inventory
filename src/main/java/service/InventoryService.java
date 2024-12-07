package service;

import context.UserContext;
import enums.InventoryType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import model.Inventory;
import records.InventoryManualRequest;

import java.util.Comparator;
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

    @Inject
    SupplierService supplierService;

    @Inject
    AlertService alertService;

    @Inject
    UserContext userContext;

    @Transactional
    public boolean createManualEntryOnInventory(InventoryManualRequest request) {
        if (Objects.isNull(request.entryDate()))
            throw new IllegalArgumentException("Invalid Entry Date!");

        Inventory newEntry = Inventory.builder()
                .batch(request.batch())
                .warehouse(request.warehouse())
                .item(itemService.findById(request.itemId()))
                .inventoryType(request.inventoryType())
                .quantity(request.quantity())
                .costPrice(request.costPrice())
                .entryDate(request.entryDate())
                .build();

        newEntry.setCreatedBy(userContext.getCurrentUsername());
        newEntry.setModifiedBy(userContext.getCurrentUsername());

        if (Objects.nonNull(request.supplier()) && request.supplier().id != null && request.supplier().id != -1) {
            newEntry.setSupplier(request.supplier());
        } else {
            newEntry.setSupplier(supplierService.getDummySupplier());
        }

        System.out.println(newEntry);
        newEntry.persistAndFlush();
        itemService.updateQuantity(newEntry);
        itemService.updateCosts(newEntry, request.retailPrice(), request.salePrice());

        //we only create alerts if the entry is to remove from the inventory
        if (request.quantity() < 0)
            alertService.createLowInventoryAlert(newEntry);
        else if (newEntry.getItem().getQuantity() >= newEntry.getItem().getMinQuantity())
            alertService.updateAlertToResolved(newEntry.getItem().getCode());

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
