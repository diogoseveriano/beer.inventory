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

}
