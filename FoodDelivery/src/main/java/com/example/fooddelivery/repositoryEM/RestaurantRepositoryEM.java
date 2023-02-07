package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.model.RestaurantManager;

import java.util.Optional;

public interface RestaurantRepositoryEM {
    Restaurant findByRestaurantManager(RestaurantManager restaurantManager);

    Optional<Restaurant> findByRestaurantManager_RestaurantManagerId(Integer id);
}
