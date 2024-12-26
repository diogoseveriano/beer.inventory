package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
public class PurchaseOrderItem extends AuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    private ItemVariant itemVariant;

    @Column(nullable = false)
    private double quantity;

    @ManyToOne
    private Unit unit;

    @Column(nullable = false)
    private BigDecimal unitPrice = BigDecimal.ZERO;

    private String batch;

}
