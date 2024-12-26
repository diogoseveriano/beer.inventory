package service;

import enums.ItemType;
import enums.alerts.AlertAction;
import enums.alerts.AlertTitle;
import enums.alerts.AlertType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import model.*;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AlertService {

    public List<Alert> findAllAlertsFromWarehouse(AlertType alertType, Warehouse warehouse) {
        return Alert.find("warehouse = ?1 and alertType = ?2 and isResolved = false", warehouse, alertType).list();
    }

    @Transactional
    public void updateAlertToResolved(String code) {
        Optional<Alert> openAlert = getOpenAlertForGivenCode(code);
        if (openAlert.isPresent()) {
            openAlert.get().setResolved(true);
            openAlert.get().persistAndFlush();
        }
    }

    /**
     * Creates a low stock alert if the minimum quantity is not met
     *
     * @param inventory
     */
    @Transactional
    public void createLowInventoryAlert(Inventory inventory, Integer variantId) {
        ItemVariant variant = ItemVariant.findById(variantId);
        Optional<Alert> existingAlert = Alert.find("itemVariant = ?1 and isResolved = false",
                variant).firstResultOptional();

        if (existingAlert.isEmpty()
                && variant.isAlertLowStock()
                && variant.getQuantity() < variant.getMinQuantity()) {
            Alert.builder()
                    .title(AlertTitle.LOW_STOCK)
                    .action(AlertAction.BUY)
                    .itemVariant(variant)
                    .warehouse(inventory.getWarehouse())
                    .alertType(inventory.getItem().getItemType().equals(ItemType.INVENTORY) ? AlertType.INVENTORY : AlertType.STOCK)
                    .content(String.format("Existing Quantity: %s - Minimum Quantity Set: %s",
                            variant.getQuantity(), variant.getMinQuantity()))
                    .isResolved(false)
                    .build().persistAndFlush();
        }

        if (existingAlert.isPresent()) {
            existingAlert.get().setContent(String.format("Existing Quantity: %s - Minimum Quantity Set: %s",
                    variant.getQuantity(), variant.getMinQuantity()));
            existingAlert.get().persistAndFlush();
        }
    }

    private Optional<Alert> getOpenAlertForGivenCode(String code) {
        return Alert.find("code = ?1 and isResolved = false", code).firstResultOptional();
    }

}
