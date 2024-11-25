package model;

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
public class ItemVariant extends AuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String code;

    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Unit unit;

    @Column(nullable = false)
    private double quantity = 0;

    private double minQuantity = 0;

    private boolean alertLowStock = false;

    //this contains the last used cost price (merely indicative)
    private BigDecimal indicativeCostPrice = BigDecimal.ZERO;

    private BigDecimal salePrice = BigDecimal.ZERO;

    private BigDecimal retailPrice = BigDecimal.ZERO;

    private boolean deprecated = false;

}
