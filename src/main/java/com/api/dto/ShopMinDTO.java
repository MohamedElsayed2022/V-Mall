package com.api.dto;

import com.api.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ShopMinDTO extends BaseEntity {
    private String shopName;
    private String description;
    private List<String> images;
    private String ownerEmail;
}
