package service;

import exceptions.SupplierAlreadyExists;
import io.smallrye.common.constraint.NotNull;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import model.Supplier;
import records.SupplierRequest;

import java.util.List;

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
                .currency(supplier.currency() == null ? "EUR" : supplier.currency())
                .isActive(true).build().persistAndFlush();

        return true;
    }

    public List<Supplier> findAll() {
        return Supplier.findAll().list();
    }

}
