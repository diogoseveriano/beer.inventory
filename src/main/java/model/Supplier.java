package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Supplier extends AuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, updatable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(length = 100)
    private String shortDescription;

    private Long nif = 999999999L;

    private String address;

    private String postalCode;

    private String city;

    @Column(length = 100)
    private String country;

    @Column(nullable = false)
    private String currency = "EUR";

    @Email
    private String email;

    private String phone;

    private String contactPerson;

    private String contactPersonPhone;

    private String contactPersonEmail;

    private String notes;

    @Column(nullable = false)
    private boolean isActive = true;

    private boolean isDummy = false;

}
