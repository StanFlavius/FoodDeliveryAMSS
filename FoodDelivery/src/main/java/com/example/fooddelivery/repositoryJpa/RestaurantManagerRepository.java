package com.example.fooddelivery.repositoryJpa;

import com.example.fooddelivery.model.RestaurantManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantManagerRepository extends JpaRepository<RestaurantManager, Integer> {

}
