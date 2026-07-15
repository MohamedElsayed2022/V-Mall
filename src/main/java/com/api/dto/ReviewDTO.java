package com.api.dto;

import com.api.base.AuditableEntity;
import com.api.base.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ReviewDTO extends AuditableEntity {
    private Integer rating;
    private String comment;
    private ShopMinDTO shop;
}
