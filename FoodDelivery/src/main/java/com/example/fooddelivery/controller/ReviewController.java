package com.example.fooddelivery.controller;

import com.example.fooddelivery.CurrentUser;
import com.example.fooddelivery.dto.review.AddReviewDto;
import com.example.fooddelivery.dto.review.OverallReviewRestaurant;
import com.example.fooddelivery.dto.review.ResponseReviewDto;
import com.example.fooddelivery.log.Logger;
import com.example.fooddelivery.mapper.ReviewMapper;
import com.example.fooddelivery.model.Review;
import com.example.fooddelivery.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok(reviewMapper.ReviewToAddReviewDto(review));
    }

    @GetMapping("/restMan/{restaurantManager}")
    public ResponseEntity<List<ResponseReviewDto>> getReviewsOfMyRestaurant(@PathVariable Integer restaurantManager){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok(reviewService.getReviewsPerRestaurantManager(restaurantManager));
    }

    @GetMapping("/rest/{restaurantId}")
    public ResponseEntity<OverallReviewRestaurant> getOverallReviewRestaurant(@PathVariable Integer restaurantId){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok(reviewService.getOverallReviewOfRestaurant(restaurantId));
    }
}
