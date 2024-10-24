package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.User;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@MappedSuperclass
public class AuditEntity extends PanacheEntity {

    @Column(updatable = false)
    @User
    String createdBy;

    @User
    String modifiedBy;

    @CreationTimestamp
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp modifiedDate;

}
