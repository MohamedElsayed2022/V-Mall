package com.api.dto;

import com.api.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ShopMinDTO extends BaseEntity {
    private String shopName;
    private String description;
    private String logo;       // 🆕 مضاف
    private String ownerEmail;
}
