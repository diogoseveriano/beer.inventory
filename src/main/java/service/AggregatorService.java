package service;

import enums.InventoryType;
import enums.ItemType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.Inventory;
import records.StatisticCard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class AggregatorService {

    @Inject
    private WarehouseService warehouseService;

    @Inject
    private InventoryService inventoryService;

    @Inject
    private ItemService itemService;

    public List<StatisticCard> getStatisticsForInventoryPage() {
        return List.of(
                createStatisticCard("Inventory Items", getTotalNumberOfItems(), "ri-box-1-fill", "primary", false),
                createStatisticCard("Purchase Orders (Pendind Delivery)", getTotalNumberOfPurchaseOrdersPendingDelivery(), "ri-ship-2-line", "info", false),
                createStatisticCard("(Average) Inventory Price", getInventoryTotalPrice(), "ri-money-euro-circle-line", "primary", true),
                createStatisticCard("Total Amount of Cereal", getTotalWeightOfCereal(), "ri-scales-line", "warning", false),
                createStatisticCard("Inventory Alerts", getTotalNumberOfInventoryAlerts(), "ri-alert-line", "error", false)
        );
    }

    public List<StatisticCard> getStatisticsForStockPage() {
        return List.of(
          createStatisticCard("Stock (Beer) Items", getTotalNumberOfFinishedProducts(), "ri-beer-line", "success", false),
          createStatisticCard("Stock Price", getStockPrice(), "ri-money-euro-circle-line", "info", true),
          createStatisticCard("Potential Profit", getPotentialProfit(), "ri-money-euro-circle-line", "warning", true),
          createStatisticCard("Stock Alerts", getTotalNumberOfStockAlerts(), "ri-alert-line", "error", false)
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

    private String getTotalNumberOfInventoryAlerts() {
        if (warehouseService.getDefaultWarehouse().isPresent()) {
            return "" + itemService.findAllInventory().stream().filter(item ->
                item.isAlertLowStock() && item.getQuantity() < item.getMinQuantity()
            ).count();
        }

        return "" + 0L;
    }

    private String getTotalNumberOfStockAlerts() {
        if (warehouseService.getDefaultWarehouse().isPresent()) {
            return "" + itemService.findAllStock().stream().filter(item ->
                    item.isAlertLowStock() && item.getQuantity() < item.getMinQuantity()
            ).count();
        }

        return "" + 0L;
    }

    //REVIEW
    private String getInventoryTotalPrice() {
        if (warehouseService.getDefaultWarehouse().isPresent()) {
            // Step 1: Get all inventories from the inventory service
            List<Inventory> allInventories = inventoryService.findAll();

            // Step 2: Group positive inventory entries by item code and calculate average cost price
            Map<String, BigDecimal> itemCodeToAvgCostPrice = new HashMap<>();

            // Group positive inventories by itemCode and accumulate the cost and quantity
            Map<String, List<Inventory>> positiveInventoryGroupedByItemCode = allInventories.stream()
                    .filter(inventory -> inventory.getQuantity() > 0) // Only include positive inventories
                    .collect(Collectors.groupingBy(inventory -> inventory.getItem().getCode()));

            // Step 3: Calculate the average cost price for each item code
            for (Map.Entry<String, List<Inventory>> entry : positiveInventoryGroupedByItemCode.entrySet()) {
                String itemCode = entry.getKey();
                List<Inventory> positiveInventories = entry.getValue();

                // Calculate total cost and total quantity for the item (positive inventories)
                BigDecimal totalCost = BigDecimal.ZERO;
                BigDecimal totalQuantity = BigDecimal.ZERO;

                for (Inventory inventory : positiveInventories) {
                    totalCost = totalCost.add(inventory.getCostPrice().multiply(BigDecimal.valueOf(inventory.getQuantity())));
                    totalQuantity = totalQuantity.add(BigDecimal.valueOf(inventory.getQuantity()));
                }

                // Calculate the average cost price, if totalQuantity is greater than zero
                if (totalQuantity.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal avgCostPrice = totalCost.divide(totalQuantity, 2, RoundingMode.HALF_UP);
                    itemCodeToAvgCostPrice.put(itemCode, avgCostPrice);
                } else {
                    itemCodeToAvgCostPrice.put(itemCode, BigDecimal.ZERO); // Default to zero if no positive quantities
                }
            }

            // Step 4: Sum all quantities (positive and negative) by item code and calculate total cost based on average cost price
            BigDecimal totalInventoryCost = BigDecimal.ZERO;

            Map<String, BigDecimal> itemCodeToTotalQuantity = new HashMap<>();

            // Sum quantities for both positive and negative inventories by item code
            for (Inventory inventory : allInventories) {
                String itemCode = inventory.getItem().getCode();
                BigDecimal quantity = BigDecimal.valueOf(inventory.getQuantity());

                // Sum the quantities for each item code (positive and negative)
                itemCodeToTotalQuantity.put(itemCode,
                        itemCodeToTotalQuantity.getOrDefault(itemCode, BigDecimal.ZERO).add(quantity));
            }

            // Calculate the total inventory cost by multiplying total quantity by average cost price
            for (Map.Entry<String, BigDecimal> entry : itemCodeToTotalQuantity.entrySet()) {
                String itemCode = entry.getKey();
                BigDecimal totalQuantity = entry.getValue();
                BigDecimal avgCostPrice = itemCodeToAvgCostPrice.getOrDefault(itemCode, BigDecimal.ZERO);

                // Multiply total quantity by average cost price to get total cost for this item
                BigDecimal totalCostForItem = totalQuantity.multiply(avgCostPrice);

                // Add the cost for this item to the total inventory cost
                totalInventoryCost = totalInventoryCost.add(totalCostForItem);
            }

            // Step 5: Return the total cost of inventory
            return totalInventoryCost.setScale(2, RoundingMode.HALF_UP).toString();
        }

        return "0"; // Return "0" if no default warehouse is found
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

    private String getTotalWeightOfCereal() {
        if (warehouseService.getDefaultWarehouse().isPresent()) {
            return "" + itemService.findAllInventory().stream()
                    .filter(item -> item.getItemType().equals(ItemType.INVENTORY)
                        && item.getCategory().getName().equals("Cereal"))
                    .map(item -> BigDecimal.valueOf(item.getQuantity()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .setScale(2, RoundingMode.HALF_UP) + " KG";
        }

        return "0";
    }
}
