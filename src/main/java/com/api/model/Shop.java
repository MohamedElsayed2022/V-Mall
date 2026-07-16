package com.api.model;

import com.api.base.AuditableEntity;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shops")
public class Shop extends AuditableEntity {
    @Column(name = "name")
    private String shopName;
    @Column(name = "description")
    private String description;

    @Column(name = "area")
    private String area;

    @Column(name = "rent_price")
    private Double rentPrice;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "total_reviews")
    private Integer totalReviews;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "shop_images",
            joinColumns = @JoinColumn(name = "shop_id")
    )
    @Column(name = "image")
    private List<String> images;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id" , nullable = false)
    private Category category;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Contract> contracts;

    @OneToMany(mappedBy = "shop" ,  cascade = CascadeType.ALL ,  fetch = FetchType.LAZY)
    private List<Review> reviews;
}
