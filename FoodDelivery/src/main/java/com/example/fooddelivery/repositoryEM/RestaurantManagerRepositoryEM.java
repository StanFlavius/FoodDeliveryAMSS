package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.RestaurantManager;

public interface RestaurantManagerRepositoryEM {
    RestaurantManager findByUserEntity_Id(Integer id);
}
