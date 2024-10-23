package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Unit / Measurements
 * e.g. Unit, Kilo, Grams...
 **/
@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class Unit extends AuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

}
