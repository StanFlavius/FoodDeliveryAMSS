package com.example.fooddelivery.dto.review;

import lombok.Data;

import java.util.List;

@Data
public class OverallReviewRestaurant {

    private String restaurantName;

    private String address;

    private String restaurantManager;

    private Double totalRating;

    private List<ResponseReviewDto> reviewList;

    public OverallReviewRestaurant(){};
    public OverallReviewRestaurant(String restaurantName, String address, String restaurantManager, Double totalRating, List<ResponseReviewDto> reviewList) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.restaurantManager = restaurantManager;
        this.totalRating = totalRating;
        this.reviewList = reviewList;
    }
}
