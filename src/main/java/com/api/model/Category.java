package com.api.model;

import com.api.base.AuditableEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category extends AuditableEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "category_logo")
    private String logo;


}
