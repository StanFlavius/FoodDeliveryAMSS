package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.review.AddReviewDto;
import com.example.fooddelivery.dto.review.OverallReviewRestaurant;
import com.example.fooddelivery.dto.review.ResponseReviewDto;
import com.example.fooddelivery.mapper.ReviewMapper;
import com.example.fooddelivery.model.Review;
import com.example.fooddelivery.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@Validated
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewMapper reviewMapper;

    @PostMapping("/{userId}/{restaurantId}")
    public ResponseEntity<ResponseReviewDto> addReview(@PathVariable Integer userId,
                                                       @PathVariable Integer restaurantId,
                                                       @RequestBody AddReviewDto reviewDto){
        Review review = reviewService.addNewReview(reviewDto, userId, restaurantId);

        return ResponseEntity.ok(reviewMapper.ReviewToAddReviewDto(review));
    }

    @GetMapping("/restMan/{restaurantManager}")
    public ResponseEntity<List<ResponseReviewDto>> getReviewsOfMyRestaurant(@PathVariable Integer restaurantManager){
        return ResponseEntity.ok(reviewService.getReviewsPerRestaurantManager(restaurantManager));
    }

    @GetMapping("/rest/{restaurantId}")
    public ResponseEntity<OverallReviewRestaurant> getOverallReviewRestaurant(@PathVariable Integer restaurantId){
        return ResponseEntity.ok(reviewService.getOverallReviewOfRestaurant(restaurantId));
    }
}
