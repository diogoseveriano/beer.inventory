package model;

import enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class PurchaseOrder extends AuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(unique = true, nullable = false)
    private String purchaseOrderReference;

    private String storeName;

    private String storeAddress;

    private String storePostalCode;

    private String storeCity;

    private String storeCountry;

    private String storeNif;

    private Date purchaseDate;

    private OrderStatus orderStatus;

    @ManyToMany
    private List<PurchaseOrderItem> items;

}
