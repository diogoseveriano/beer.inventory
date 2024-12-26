package model;

import enums.alerts.AlertAction;
import enums.alerts.AlertTitle;
import enums.alerts.AlertType;
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
public class Alert extends AuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_variant_id", nullable = false)
    private ItemVariant itemVariant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertTitle title;

    @Column(length = 500)
    private String content;

    @Enumerated(EnumType.STRING)
    private AlertAction action;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertType alertType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Column(nullable = false)
    private boolean isResolved = false;

    @PrePersist
    @PreUpdate
    public void validate() {
        if (isResolved && action == null) {
            throw new IllegalStateException("Resolved alerts must have an associated action.");
        }
    }
}