package service;

import context.UserContext;
import enums.InventoryType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import model.Inventory;
import model.ItemVariant;
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

        ItemVariant variant = ItemVariant.findById(request.itemVariantId());

        Inventory newEntry = Inventory.builder()
                .batch(request.batch())
                .warehouse(request.warehouse())
                .item(itemService.findById(request.itemId()))
                .inventoryType(request.inventoryType())
                .quantity(request.quantity())
                .costPrice(request.costPrice())
                .entryDate(request.entryDate())
                .variant(itemService.findVariantById(request.itemVariantId()))
                .build();

        newEntry.setCreatedBy(userContext.getCurrentUsername());
        newEntry.setModifiedBy(userContext.getCurrentUsername());

        if (Objects.nonNull(request.supplier()) && request.supplier().id != null && request.supplier().id != -1) {
            newEntry.setSupplier(request.supplier());
        } else {
            newEntry.setSupplier(supplierService.getDummySupplier());
        }

        newEntry.persistAndFlush();
        itemService.updateQuantity(newEntry, request.itemVariantId());
        itemService.updateCosts(newEntry, request.itemVariantId(), request.retailPrice(), request.salePrice());

        //we only create alerts if the entry is to remove from the inventory
        if (request.quantity() < 0)
            alertService.createLowInventoryAlert(newEntry, request.itemVariantId());
        else if (variant.getQuantity() >= variant.getMinQuantity())
            alertService.updateAlertToResolved(newEntry.getItem().getCode());

        return true;
    }

    //REMOVE
    public List<Inventory> findAll() {
        return Inventory.findAll().list();
    }

    public List<Inventory> findInventoryByWarehouse(String warehouse) {
        if ("ALL".equals(warehouse)) {
            return Inventory.find("inventoryType <> ?1", InventoryType.FINISHED_PRODUCT).list();
        } else {
            return Inventory.find("warehouse = ?1 and inventoryType <> ?2",
                    warehouseService.getWarehouseById(Long.parseLong(warehouse)), InventoryType.FINISHED_PRODUCT).list();
        }
    }

    public List<Inventory> findStockByWarehouse(String warehouse) {
        if ("ALL".equals(warehouse)) {
            return Inventory.find("inventoryType = ?1", InventoryType.FINISHED_PRODUCT).list();
        } else {
            return Inventory.find("warehouse = ?1 and inventoryType = ?2",
                    warehouseService.getWarehouseById(Long.parseLong(warehouse)), InventoryType.FINISHED_PRODUCT).list();
        }
    }

    public Inventory findInventoryOrStockById(long id) {
        return Inventory.findById(id);
    }

}
