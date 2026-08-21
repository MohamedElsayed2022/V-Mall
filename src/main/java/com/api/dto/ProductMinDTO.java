package com.api.dto;

import com.api.base.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@RequiredArgsConstructor
public class ProductMinDTO extends BaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
}
