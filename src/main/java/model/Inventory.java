package model;

import enums.InventoryType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Inventory extends AuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Warehouse warehouse;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Item item;

    private InventoryType inventoryType;

    @Column(nullable = false)
    private double quantity = 0;

    private double minQuantity = 0;

    private boolean alertLowStock = false;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Unit unit;

    @Column(nullable = false)
    private BigDecimal costPrice = BigDecimal.ZERO;

    private String notes;

}
