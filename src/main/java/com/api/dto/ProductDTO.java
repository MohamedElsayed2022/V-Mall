package com.api.dto;

import com.api.model.Shop;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String sku;
    private ShopMinDTO shop;
//    private Long shopId;
 //   private Long categoryId;
    private List<String> images;
    private Boolean active;
    private CategoryDTO category;

}
