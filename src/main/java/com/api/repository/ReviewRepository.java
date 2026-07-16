package com.api.repository;

import com.api.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT COUNT(r) FROM Review r WHERE r.shop.id = :shopId")
    long countByShopId(@Param("shopId") Long shopId);
}
