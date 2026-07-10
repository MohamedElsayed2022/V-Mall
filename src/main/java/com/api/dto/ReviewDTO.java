package com.api.dto;

import com.api.base.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ReviewDTO extends BaseEntity {
    private Integer rating;
    private String comment;
}
