package com.api.dto;

import com.api.base.AuditableEntity;
import com.api.model.Contract;
import com.api.model.Review;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ShopResponseDTO extends AuditableEntity {
    private String shopName;
    private String description;
    private List<String> images;
    private OwnerDTO owner;
    private CategoryDTO category;
    private ContractDTO contract;
    private List<ReviewDTO> reviews;
    private String area;
    private Double rentPrice;
    private Double averageRating;
    private Integer totalReviews;

    //   private Contract contract;

}
