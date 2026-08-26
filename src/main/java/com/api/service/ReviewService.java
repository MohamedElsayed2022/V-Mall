package com.api.service;

import com.api.dto.ReviewDTO;
import com.api.dto.ShopRevDTO;
import com.api.model.Review;
import com.api.model.Shop;
import com.api.repository.ReviewRepository;
import com.api.repository.ShopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ShopRepository shopRepository;
    public List<ReviewDTO> getAllReviews(Long shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Shop not found with id: " + shopId
                        )
                );

        List<Review> reviews =  reviewRepository.findByShopId(shopId);
        return reviews.stream().map(review -> {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setId(review.getId());
            reviewDTO.setComment(review.getComment());
            reviewDTO.setRating(review.getRating());
            reviewDTO.setCreatedDate(review.getCreatedDate());
            reviewDTO.setLastModifiedDate(review.getLastModifiedDate());
            if(review.getShop() != null) {
                ShopRevDTO shopMinDTO = new ShopRevDTO();
                shopMinDTO.setId(review.getShop().getId());
                shopMinDTO.setShopName(review.getShop().getShopName());
                reviewDTO.setShop(shopMinDTO);
            }
            return reviewDTO;
        }).collect(Collectors.toList());
    }

    public ReviewDTO createReview(Review review, Long shopId) {

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Shop not found with id: " + shopId
                        )
                );

        review.setShop(shop);

        Review savedReview = reviewRepository.save(review);

        ShopRevDTO shopMinDTO = new ShopRevDTO();
        shopMinDTO.setId(shop.getId());
        shopMinDTO.setShopName(shop.getShopName());

        ReviewDTO reviewDTO = new ReviewDTO();

        reviewDTO.setShop(shopMinDTO);
        reviewDTO.setId(review.getId());
        reviewDTO.setCreatedDate(savedReview.getCreatedDate());
        reviewDTO.setComment(savedReview.getComment());
        reviewDTO.setRating(savedReview.getRating());
        reviewDTO.setLastModifiedDate(savedReview.getLastModifiedDate());

        return reviewDTO;
    }
}
