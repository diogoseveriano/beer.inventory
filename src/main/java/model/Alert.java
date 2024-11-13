package model;

import enums.alerts.AlertAction;
import enums.alerts.AlertTitle;
import enums.alerts.AlertType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    private String code;

    private AlertTitle title;

    private String content;

    private AlertAction action;

    private AlertType alertType;

    @ManyToOne
    private Warehouse warehouse;

    @Column(nullable = false)
    private boolean isResolved;

}
