package service;

import enums.InventoryType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import records.StatisticCard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@ApplicationScoped
public class AggregatorService {

    @Inject
    private WarehouseService warehouseService;

    @Inject
    private InventoryService inventoryService;

    public List<StatisticCard> getStatisticsForInventoryPage() {
        return List.of(
                createStatisticCard("Inventory Items", getTotalNumberOfItems(), "ri-box-1-fill", "primary", false),
                createStatisticCard("Purchase Orders (Pendind Delivery)", getTotalNumberOfPurchaseOrdersPendingDelivery(), "ri-ship-2-line", "info", false),
                createStatisticCard("Inventory Price", getInventoryTotalPrice(), "ri-money-euro-circle-line", "info", true),
                createStatisticCard("Inventory Alerts", getTotalNumberOfInventoryAlerts(), "ri-alert-line", "error", false)
        );
    }

    public List<StatisticCard> getStatisticsForStockPage() {
        return List.of(
          createStatisticCard("Stock (Beer) Items", getTotalNumberOfFinishedProducts(), "ri-beer-line", "success", false),
          createStatisticCard("Stock Price", getStockPrice(), "ri-money-euro-circle-line", "info", true),
          createStatisticCard("Potential Profit", getPotentialProfit(), "ri-money-euro-circle-line", "warning", true),
          createStatisticCard("Stock Alerts", getTotalNumberOfInventoryAlerts(), "ri-alert-line", "error", false)
        );
    }

    private StatisticCard createStatisticCard(String title, String stats, String avatar, String color, boolean isMoney) {
        return StatisticCard.builder()
                .title(title)
                .avatarIcon(avatar)
                .stats(stats)
                .color(color)
                .isMoney(isMoney)
                .build();
    }

    private String getTotalNumberOfItems() {
        if (warehouseService.getDefaultWarehouse().isPresent()) {
            return "" + inventoryService.findAll().stream().filter(inventory ->
                    inventory.getWarehouse().equals(warehouseService.getDefaultWarehouse().get()) &&
                    !inventory.getInventoryType().equals(InventoryType.FINISHED_PRODUCT)).count();
        }

        return "" + inventoryService.findAll().stream().filter(inventory ->
                !inventory.getInventoryType().equals(InventoryType.FINISHED_PRODUCT)).count();
    }

    private String getTotalNumberOfFinishedProducts() {
        if (warehouseService.getDefaultWarehouse().isPresent()) {
            return "" + inventoryService.findAll().stream().filter(inventory ->
                    inventory.getWarehouse().equals(warehouseService.getDefaultWarehouse().get()) &&
                    inventory.getInventoryType().equals(InventoryType.FINISHED_PRODUCT)).count();
        }

        return "" + inventoryService.findAll().stream().filter(inventory ->
                inventory.getInventoryType().equals(InventoryType.FINISHED_PRODUCT)).count();
    }

    //#TODO :: IMPLEMENT SERVICE
    private String getTotalNumberOfPurchaseOrdersPendingDelivery() {
        return "" + 0L;
    }

    //#TODO :: IMPLEMENT SERVICE
    private String getTotalNumberOfInventoryAlerts() {
        return "" + 0L;
    }

    private String getInventoryTotalPrice() {
        if (warehouseService.getDefaultWarehouse().isPresent()) {
            return "" + inventoryService.findAll().stream()
                    .filter(inventory -> !inventory.getInventoryType().equals(InventoryType.FINISHED_PRODUCT))
                    .map(inventory -> inventory.getCostPrice()
                            .multiply(BigDecimal.valueOf(inventory.getQuantity()))
                            .setScale(2, RoundingMode.HALF_UP))
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        return "0";
    }

    private String getStockPrice() {
        if (warehouseService.getDefaultWarehouse().isPresent()) {
            return "" + inventoryService.findAll().stream()
                    .filter(inventory -> inventory.getInventoryType().equals(InventoryType.FINISHED_PRODUCT))
                    .map(inventory -> inventory.getCostPrice()
                            .multiply(BigDecimal.valueOf(inventory.getQuantity()))
                            .setScale(2, RoundingMode.HALF_UP))
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        return "0";
    }

    private String getPotentialProfit() {
        if (warehouseService.getDefaultWarehouse().isPresent()) {
            return "" + inventoryService.findAll().stream()
                    .filter(inventory -> inventory.getInventoryType().equals(InventoryType.FINISHED_PRODUCT) &&
                            inventory.getSalePrice() != null)
                    .map(inventory -> inventory.getSalePrice()
                            .multiply(BigDecimal.valueOf(inventory.getQuantity()))
                            .setScale(2, RoundingMode.HALF_UP))
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        return "0";
    }
}
