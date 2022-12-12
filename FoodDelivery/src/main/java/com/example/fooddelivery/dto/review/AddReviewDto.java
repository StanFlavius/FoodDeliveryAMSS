package com.example.fooddelivery.dto.review;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data
public class AddReviewDto {

    private Integer rating;

    private String reviewText;

    public AddReviewDto(Integer rating, String reviewText){
        this.rating = rating;
        this.reviewText = reviewText;
    }
}
