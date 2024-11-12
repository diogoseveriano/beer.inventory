package model;

import enums.ItemType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Item extends AuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, updatable = false, unique = true)
    private String code;

    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    //e.g. Cereal, Beer, Keg, etc.
    private ItemCategory category;

    @Column(name = "item_type")
    private ItemType itemType;

    private String brand;

    private String description;

    private String notes;

    @Column(nullable = false)
    private double quantity = 0;

    private double minQuantity = 0;

    private boolean alertLowStock = false;

    private boolean deprecated = false;

}
