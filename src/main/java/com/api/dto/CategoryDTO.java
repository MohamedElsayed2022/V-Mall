package com.api.dto;

import com.api.base.AuditableEntity;
import com.api.base.BaseEntity;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CategoryDTO extends AuditableEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "category_logo")
    private String logo;
}
