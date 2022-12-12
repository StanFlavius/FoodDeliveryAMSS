package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.product.GetProductListDto;
import com.example.fooddelivery.dto.review.AddReviewDto;
import com.example.fooddelivery.dto.review.ResponseReviewDto;
import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ResponseReviewDto ReviewToAddReviewDto(Review review){
        return new ResponseReviewDto(
                review.getRating(),
                review.getText(),
                review.getUser().getFirstName() + review.getUser().getLastName()
        );
    }

}
