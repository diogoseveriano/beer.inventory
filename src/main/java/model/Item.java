package model;

import enums.ItemType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "code"))
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

    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    //e.g. Cereal, Beer, Keg, etc.
    private ItemCategory category;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemVariant> variants;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false )
    private ItemType itemType;

    private String brand;

    private String description;

    private String notes;

    private Timestamp deprecatedAt;

    private boolean deprecated = false;

    @Transient
    public boolean isDeprecated() {
        return deprecated;
    }

}
