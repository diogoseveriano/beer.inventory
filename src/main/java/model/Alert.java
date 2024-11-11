package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    private String title;

    private String content;

    private String action;

    @Column(nullable = false)
    private boolean markedHasRead;

}
