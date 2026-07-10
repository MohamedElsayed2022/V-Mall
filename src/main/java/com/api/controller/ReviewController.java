package com.api.controller;

import com.api.model.Review;
import com.api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class ReviewController {

    private final ReviewService reviewService;

    @RequestMapping("reviews")
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @PostMapping("review")
    public Review createReview(@RequestBody Review review) {

        System.out.println("Rating = " + review.getRating());
        System.out.println("Comment = " + review.getComment());
        System.out.println("Shop = " + review.getShop());

        if (review.getShop() != null) {
            System.out.println("Shop Id = " + review.getShop().getId());
        }

        return reviewService.createReview(review);
    }
}
