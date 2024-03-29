package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.review.AddReviewDto;
import com.example.fooddelivery.dto.review.OverallReviewRestaurant;
import com.example.fooddelivery.dto.review.ResponseReviewDto;
import com.example.fooddelivery.mapper.ReviewMapper;
import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.model.RestaurantManager;
import com.example.fooddelivery.model.Review;
import com.example.fooddelivery.repositoryEM.RestaurantRepositoryEM;
import com.example.fooddelivery.repositoryEM.ReviewRepositoryEM;
import com.example.fooddelivery.repositoryJpa.NormalUserRepository;
import com.example.fooddelivery.repositoryJpa.RestaurantManagerRepository;
import com.example.fooddelivery.repositoryJpa.RestaurantRepository;
import com.example.fooddelivery.repositoryJpa.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    NormalUserRepository normalUserRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantManagerRepository restaurantManagerRepository;

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    RestaurantRepositoryEM restaurantRepositoryEM;

    @Autowired
    ReviewRepositoryEM reviewRepositoryEM;

    public Review addNewReview(AddReviewDto reviewDto, Integer userId, Integer restaurantId){
        Review review = new Review();
        review.setRating(reviewDto.getRating());
        review.setText(reviewDto.getReviewText());
        review.setUser(normalUserRepository.getById(userId));
        review.setRestaurant(restaurantRepository.getById(restaurantId));

        return reviewRepository.save(review);
    }

    public List<ResponseReviewDto> getReviewsPerRestaurantManager(Integer restaurantManagerId){
        RestaurantManager restaurantManager = restaurantManagerRepository.getById(restaurantManagerId);

        Restaurant restaurant = restaurantRepositoryEM.findByRestaurantManager(restaurantManager);

        List<Review> reviewList = reviewRepositoryEM.findByRestaurant(restaurant);

        List<ResponseReviewDto> reviewDtos = new ArrayList<>();
        for (Review review : reviewList) {
            ResponseReviewDto reviewDto = reviewMapper.ReviewToAddReviewDto(review);
            reviewDtos.add(reviewDto);
        }

        return reviewDtos;
    }

    public OverallReviewRestaurant getOverallReviewOfRestaurant(Integer restaurantId){
        Restaurant restaurant = restaurantRepository.getById(restaurantId);

        List<Review> reviewList = reviewRepositoryEM.findByRestaurant(restaurant);

        Integer sumRatings = 0;
        Integer countRatings = 0;
        List<ResponseReviewDto> reviewDtos = new ArrayList<>();
        for (Review review : reviewList) {
            ResponseReviewDto reviewDto = reviewMapper.ReviewToAddReviewDto(review);
            reviewDtos.add(reviewDto);
            countRatings++;
            sumRatings += review.getRating();
        }

        OverallReviewRestaurant overallReviewRestaurant = new OverallReviewRestaurant();
        overallReviewRestaurant.setRestaurantId(restaurant.getRestaurantId());
        overallReviewRestaurant.setRestaurantName(restaurant.getName());
        overallReviewRestaurant.setRestaurantManager(restaurant.getRestaurantManager().getFirstName() + " " + restaurant.getRestaurantManager().getLastName());
        overallReviewRestaurant.setReviewList(reviewDtos);
        overallReviewRestaurant.setAddress(restaurant.getAddress());
        overallReviewRestaurant.setTotalRating(countRatings > 0 ? (double) (sumRatings / countRatings) : 0);

        return overallReviewRestaurant;
    }
}
