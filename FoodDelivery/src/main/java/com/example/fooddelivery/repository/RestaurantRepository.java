package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.model.RestaurantManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Restaurant findByRestaurantManager(RestaurantManager restaurantManager);

}
