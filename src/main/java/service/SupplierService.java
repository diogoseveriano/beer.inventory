package service;

import enums.Defaults;
import exceptions.SupplierAlreadyExists;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import model.Supplier;
import records.SupplierRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class SupplierService {

    @Transactional
    public boolean createSupplier(@NotNull SupplierRequest supplier) {
        if (Supplier.find("code = ?1 OR nif = ?2", supplier.code(), supplier.nif()).count() > 0)
            throw new SupplierAlreadyExists(supplier.code());

        Supplier.builder()
                .code(supplier.code())
                .nif(supplier.nif())
                .name(supplier.supplierName())
                .email(supplier.email())
                .phone(supplier.phone())
                .address(supplier.address())
                .postalCode(supplier.postalCode())
                .city(supplier.city())
                .country(supplier.country())
                .shortDescription(supplier.shortDescription())
                .notes(supplier.notes())
                .contactPerson(supplier.contactPerson())
                .contactPersonEmail(supplier.contactPersonEmail())
                .contactPersonPhone(supplier.contactPersonPhone())
                .currency(supplier.currency() == null ? Defaults.DEFAULT_CURRENCY : supplier.currency())
                .isDummy(false)
                .isActive(true).build().persistAndFlush();

        return true;
    }

    public List<Supplier> findAll() {
        return  Supplier.findAll()
                .list()
                .stream()
                .map(w -> (Supplier) w)
                .filter(w -> !w.isDummy())
                .collect(Collectors.toList());
    }

    public boolean existsById(@NotNull Integer id) {
        return Supplier.find("id = ?1", id).count() > 0;
    }

    @Transactional
    public void deleteSupplierById(@NotNull Integer id) {
        Supplier.deleteById(id);
    }

    @Transactional
    public Supplier getDummySupplier() {
        Optional<Supplier> dummy = Supplier.find("isDummy", true).firstResultOptional();
        if (dummy.isPresent())
            return dummy.get();
        else {
            Supplier.builder()
                    .code("DUMMY")
                    .currency("EUR")
                    .nif(-1L)
                    .notes("DUMMY SUPPLIER FOR INTERNAL STOCK")
                    .isDummy(true)
                    .name("DUMMY")
                    .build().persistAndFlush();
            return getDummySupplier();
        }
    }
}
