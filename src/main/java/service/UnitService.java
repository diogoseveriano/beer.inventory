package service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import model.Unit;
import records.UnitRequest;

import java.util.List;

@ApplicationScoped
public class UnitService {

    @Transactional
    public boolean createUnit(UnitRequest unit) {
        if (Unit.find("name", unit.name()).count() > 0)
            return false;

        Unit.builder().name(unit.name()).build().persistAndFlush();
        return true;
    }

    public List<Unit> findAll() {
        return Unit.findAll().list();
    }

    public Unit findById(int id) {
        return Unit.findById(id);
    }

    public boolean exists(int id) {
        return Unit.find("id", id).count() > 0;
    }

    @Transactional
    public void deleteUnitById(int id) {
        Unit.deleteById(id);
    }

}
