package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.model.Review;

import java.util.List;

public interface ReviewRepositoryEM {
    List<Review> findByRestaurant(Restaurant restaurant);
}
