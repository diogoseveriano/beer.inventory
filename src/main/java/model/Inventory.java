package model;

import enums.InventoryType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

    private String batch;

    @ManyToOne(fetch = FetchType.LAZY)
    private Supplier supplier;

    private InventoryType inventoryType;

    @Column(nullable = false)
    private double quantity = 0.0;

    @Column(nullable = false)
    private BigDecimal costPrice = BigDecimal.ZERO;

    private String notes;

    @Column(name = "entry_date")
    private Date entryDate;

    // TODO PENSAR BEM NESTA TEMATICA DA VALIDADE, PORQUE SE EXPIRA DEVERIAMOS ALERTAR, PENSAR COMO!!!
    private Date expirationDate;

    @Override
    public void setCreatedBy(String createdBy) {
        super.setCreatedBy(createdBy);
    }

    @Override
    public void setModifiedBy(String modifiedBy) {
        super.setModifiedBy(modifiedBy);
    }
}
