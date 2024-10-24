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
@Getter
@Setter
public class Inventory extends AuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Item item;

    private InventoryType inventoryType;

    @Column(nullable = false)
    private double quantity = 0;

    private double minQuantity = 0;

    private boolean alertLowStock = false;

    @ManyToOne
    private Unit unit;

    @Column(nullable = false)
    private BigDecimal costPrice = BigDecimal.ZERO;

    private String notes;

}
