package model;

import enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PurchaseOrder extends AuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false)
    private String purchaseOrderReference;

    private String notes;

    @ManyToOne
    private Supplier supplier;

    private BigDecimal totalPrice;

    private BigDecimal vatAmount;

    // e.g. 23%
    private BigDecimal vatPercentage;

    private Date purchaseDate;

    private Date dateReceived;

    private OrderStatus orderStatus;

    @ManyToMany
    private List<PurchaseOrderItem> items;

}
