package com.api.service;

import com.api.model.Review;
import com.api.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public Review createReview(Review review){
        return reviewRepository.save(review);
    }
}
