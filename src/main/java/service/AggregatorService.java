package service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.Unit;
import records.AggregatorDto;
import records.StatisticCard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class AggregatorService {

    @Inject
    private InventoryService inventoryService;

    @Inject
    private ItemService itemService;

    public List<StatisticCard> getStatisticsForInventoryPage(String warehouse) {
        return List.of(
                //createStatisticCard("Purchase Orders (Pendind Delivery)", getTotalNumberOfPurchaseOrdersPendingDelivery(), "ri-ship-2-line", "info", false),
                //createStatisticCard("(Average) Inventory Price", getInventoryTotalPrice(), "ri-money-euro-circle-line", "primary", true),
                //createStatisticCard("Total Amount of Cereal", getTotalWeightOfCereal(), "ri-scales-line", "warning", false),
                createStatisticCard("Inventory Movements", getTotalNumberOfInventoryTransactions(warehouse), "ri-box-1-fill", "primary", false)
                //createStatisticCard("Inventory Alerts", getTotalNumberOfInventoryAlerts(), "ri-alert-line", "error", false)
        );
    }

    public List<StatisticCard> getStatisticsForStockPage(String warehouse) {
        return List.of(
          //createStatisticCard("Stock Price", getStockPrice(), "ri-money-euro-circle-line", "info", true),
          //createStatisticCard("Potential Profit", getPotentialProfit(), "ri-money-euro-circle-line", "warning", true),
          createStatisticCard("Stock Movements", getTotalNumberOfStockTransactions(warehouse), "ri-beer-line", "success", false)
          //createStatisticCard("Stock Alerts", getTotalNumberOfStockAlerts(), "ri-alert-line", "error", false)
        );
    }

    /**
     * Gets the counting of items grouped by variant
     * @param warehouse
     * @return List of grouped variants
     */
    public List<StatisticCard> getGroupedVariantStock(String warehouse) {
        // Group inventory by variant ID
        List<AggregatorDto> aggregatorDtos = inventoryService
                .findStockByWarehouse(warehouse)
                .stream()
                .collect(Collectors.groupingBy(inventory -> inventory.getVariant().id))
                .values()
                .stream()
                .map(inventories -> {
                    // Aggregate data for each group
                    String variantName = inventories.stream()
                            .map(inventory -> inventory.getVariant().getName())
                            .findFirst()
                            .orElse("UNK");
                    String title = inventories.stream()
                            .map(inventory -> inventory.getItem().getName())
                            .distinct()
                            .collect(Collectors.joining(", ")) + " - " + variantName;

                    BigDecimal totalQuantity = inventories.stream()
                            .map(inventory -> BigDecimal.valueOf(inventory.getQuantity()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    String unit = inventories.stream()
                            .map(inventory -> inventory.getVariant().getUnit())
                            .map(Unit::getName)
                            .findFirst()
                            .orElse("UNK");

                    return AggregatorDto.builder()
                            .title(title)
                            .unit(unit)
                            .unitAmount(totalQuantity.setScale(0, RoundingMode.DOWN))
                            .build();
                })
                .toList();

        // Convert AggregatorDto to StatisticCard
        return aggregatorDtos.stream()
                .map(aggregator -> createStatisticCard(
                        aggregator.title(),
                        aggregator.unitAmount() + " " + aggregator.unit(),
                        "",
                        aggregator.unitAmount().compareTo(BigDecimal.valueOf(0)) <= 0 ? "error" : "success", //use the min quantity for this
                        false))
                .toList();
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

    private String getTotalNumberOfInventoryTransactions(String warehouse) {
        return "" + inventoryService.findInventoryByWarehouse(warehouse).size();
    }

    private String getTotalNumberOfStockTransactions(String warehouse) {
        return "" + inventoryService.findStockByWarehouse(warehouse).size();
    }
}
