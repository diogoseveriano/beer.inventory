package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class User extends AuditEntity implements Serializable {

    @Column(nullable = false)
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false)
    private String roles;

    @Column(unique = true, nullable = false)
    @Email
    private String email;

    @Column
    private String password;

    @Column(name = "is_active", nullable = false)
    private boolean active;

}