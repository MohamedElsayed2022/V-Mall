package com.api.controller;

import com.api.dto.ReviewDTO;
import com.api.model.Review;
import com.api.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class ReviewController {

    private final ReviewService reviewService;
    @RequestMapping("reviews")
    public List<ReviewDTO> getAllReviews(@RequestParam Long shopId){
        return reviewService.getAllReviews(shopId);
    }
    @PostMapping("review")
    public ReviewDTO createReview(@RequestBody Review review ,@RequestParam Long shopId){
        return reviewService.createReview(review , shopId);
    }
}
