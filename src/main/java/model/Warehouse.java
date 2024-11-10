package model;

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
public class Warehouse extends AuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private String address;

    private String postalCode;

    private String city;

    private String country;

    private boolean isCustomsRegistered;

}
