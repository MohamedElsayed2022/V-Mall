package com.api.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class AuditableEntity extends BaseEntity  {
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime lastModifiedDate;
}
