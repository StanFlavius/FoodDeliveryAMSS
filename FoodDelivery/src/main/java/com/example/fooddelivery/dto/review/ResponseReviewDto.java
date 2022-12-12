package com.example.fooddelivery.dto.review;

import lombok.Data;

@Data
public class ResponseReviewDto {

    private Integer rating;

    private String reviewText;

    private String userName;

    public ResponseReviewDto(Integer rating, String reviewText, String userName){
        this.rating = rating;
        this.reviewText = reviewText;
        this.userName = userName;
    }
}
