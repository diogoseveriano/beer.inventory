package service;

import jakarta.enterprise.context.ApplicationScoped;
import model.Warehouse;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class WarehouseService {

    public Optional<Warehouse> getDefaultWarehouse() {
        return Warehouse.find("defaultWarehouse", true).firstResultOptional();
    }

    public List<Warehouse> getWarehouseList() {
        return Warehouse.findAll().list();
    }

}
