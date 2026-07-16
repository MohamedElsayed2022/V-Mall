package com.api.service;

import com.api.dto.ReviewDTO;
import com.api.dto.ShopMinDTO;
import com.api.model.Review;
import com.api.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    public List<ReviewDTO> getAllReviews(){
        List<Review> reviews =  reviewRepository.findAll();
        return reviews.stream().map(review -> {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setId(review.getId());
            reviewDTO.setComment(review.getComment());
            reviewDTO.setRating(review.getRating());
            reviewDTO.setCreatedDate(review.getCreatedDate());
            reviewDTO.setLastModifiedDate(review.getLastModifiedDate());
            if(review.getShop() != null) {
                ShopMinDTO shopMinDTO = new ShopMinDTO();
                shopMinDTO.setId(review.getShop().getId());
                shopMinDTO.setDescription(review.getShop().getDescription());
                shopMinDTO.setShopName(review.getShop().getShopName());
                shopMinDTO.setImages(review.getShop().getImages());
                shopMinDTO.setOwnerEmail(review.getShop().getOwner().getEmail());
                reviewDTO.setShop(shopMinDTO);
            }
            return reviewDTO;
        }).collect(Collectors.toList());
    }

    public Review createReview(Review review){
        return reviewRepository.save(review);
    }
}
