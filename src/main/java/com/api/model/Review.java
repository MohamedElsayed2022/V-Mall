package com.api.model;

import com.api.base.AuditableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review extends AuditableEntity {
    @Min(1)
    @Max(5)
    @Column(name = "rating" , nullable = false)
    private Integer rating;
    @Column(name = "comment" , length = 500)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "shop_id" , nullable = false)
    private Shop shop;

}
