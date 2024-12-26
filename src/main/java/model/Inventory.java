package model;

import enums.InventoryType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory extends AuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Warehouse warehouse;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    private ItemVariant variant;

    @Column(nullable = false)
    private String batch;

    @ManyToOne(fetch = FetchType.LAZY)
    private Supplier supplier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InventoryType inventoryType;

    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false)
    private BigDecimal costPrice = BigDecimal.ZERO;

    @Column(length = 500)
    private String notes;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;
}